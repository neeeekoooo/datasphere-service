/*
 * Copyright 2019, Huahuidata, Inc.
 * DataSphere is licensed under the Mulan PSL v1.
 * You can use this software according to the terms and conditions of the Mulan PSL v1.
 * You may obtain a copy of Mulan PSL v1 at:
 * http://license.coscl.org.cn/MulanPSL
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE.
 * See the Mulan PSL v1 for more details.
 */

package com.datasphere.server.domain.workspace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

import com.datasphere.server.common.exception.BadRequestException;
import com.datasphere.server.common.exception.ResourceNotFoundException;
import com.datasphere.server.domain.notebook.Notebook;
import com.datasphere.server.domain.notebook.NotebookConnector;
import com.datasphere.server.domain.notebook.NotebookRepository;
import com.datasphere.server.domain.notebook.connector.HttpRepository;
import com.datasphere.server.domain.workbook.DashBoard;
import com.datasphere.server.domain.workbook.DashBoardPredicate;
import com.datasphere.server.domain.workbook.DashboardRepository;
import com.datasphere.server.domain.workbook.WorkBook;
import com.datasphere.server.domain.workspace.folder.Folder;

/**
 * Created by aladin on 2019. 12. 21..
 */
@RepositoryRestController
public class BookController {

  private static Logger LOGGER = LoggerFactory.getLogger(BookController.class);

  @Autowired
  BookService bookService;

  @Autowired
  BookTreeService bookTreeService;


  @Autowired
  BookRepository bookRepository;

  @Autowired
  DashboardRepository dashboardRepository;

  @Autowired
  NotebookRepository notebookRepository;

  @Autowired
  HttpRepository httpRepository;

  @Autowired
  PagedResourcesAssembler pagedResourcesAssembler;

  @RequestMapping(path = "/books/{bookId}/dashboards", method = RequestMethod.GET)
  public @ResponseBody
  ResponseEntity<?> findBooksInWorkspace(@PathVariable("bookId") String bookId,
                                         @RequestParam(value = "nameContains", required = false) String nameContains,
                                         Pageable pageable,
                                         PersistentEntityResourceAssembler resourceAssembler) {

    Book book = bookRepository.findById(bookId).get();
    if(book == null) {
      throw new ResourceNotFoundException(bookId);
    }

    final Page<DashBoard> pages;
    if(book instanceof WorkBook) {
      pages = dashboardRepository.findAll(DashBoardPredicate.searchListInWorkBook(bookId, nameContains), pageable);
    } else if(book instanceof Folder) {
      pages = dashboardRepository.findAll(DashBoardPredicate.searchListInFolder(bookId, nameContains), pageable);
    } else {
      throw new BadRequestException("Not supported item. Only choose Folder or Workbook item");
    }

    return ResponseEntity.ok(pagedResourcesAssembler.toResource(pages, resourceAssembler));
  }

  @RequestMapping(path = {"/books/{bookIds}/move", "/books/{bookIds}/move/{folderId}"}, method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<?> move(@PathVariable("bookIds") List<String> bookIds,
                                              @PathVariable("folderId") Optional<String> folderId,
                                              @RequestParam("toWorkspace") Optional<String> toWorkspace) {

    if(folderId.isPresent() && bookRepository.findById(folderId.get()) == null) {
      throw new ResourceNotFoundException(folderId.get());
    }

    if(folderId.isPresent() && Folder.ROOT.equals(folderId.get())) {
      folderId = Optional.empty();
    }

    for (String bookId : bookIds) {
      Book targetBook = bookRepository.findById(bookId).get();
      if(targetBook == null) {
        continue;
      }

      bookService.move(targetBook, folderId, toWorkspace);

    }

    return ResponseEntity.noContent().build();
  }

  @RequestMapping(path = {"/books/{bookId}/copy", "/books/{bookId}/copy/{folderId}"}, method = RequestMethod.POST)
  public ResponseEntity<?> copyBook(@PathVariable("bookId") String bookId,
                                    @PathVariable("folderId") Optional<String> folderId,
                                    @RequestParam("toWorkspace") Optional<String> toWorkspace,
                                    PersistentEntityResourceAssembler resourceAssembler) {

    Book book = bookRepository.findById(bookId).get();
    if(book == null) {
      return ResponseEntity.notFound().build();
    }

    if(folderId.isPresent() && bookRepository.findById(folderId.get()) == null) {
      throw new ResourceNotFoundException(folderId.get());
    }

    if(folderId.isPresent() && Folder.ROOT.equals(folderId.get())) {
      folderId = Optional.empty();
    }

    if(!(book instanceof WorkBook)) {
      throw new RuntimeException("Not supported book type.");
    }

    WorkBook copiedBook = bookService.copy(book, folderId, toWorkspace);

    return ResponseEntity.ok(resourceAssembler.toResource(copiedBook));

  }

  @RequestMapping(path = "/books/{bookIds}", method = RequestMethod.DELETE)
  public @ResponseBody ResponseEntity<?> multiDelete(@PathVariable("bookIds") List<String> bookIds) {
    for(String bookId : bookIds) {
      Book book = bookRepository.findById(bookId).get();
      if(book == null) {
        LOGGER.warn("Fail to find book : {}", bookId);
        continue;
      } else if(book.getType().equals("notebook")) {
        Notebook notebook = notebookRepository.findById(bookId).get();
        NotebookConnector connector = notebook.getConnector();
        connector.setHttpRepository(httpRepository);
        connector.deleteNotebook(notebook.getaLink());
        notebookRepository.delete(notebook);
      }
      bookRepository.delete(book);
      bookTreeService.deleteTree(book);
    }

    return ResponseEntity.noContent().build();
  }
}
