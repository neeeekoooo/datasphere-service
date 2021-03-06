/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.datasphere.server.domain.workbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;

import com.datasphere.server.domain.workspace.BookTreeService;

/**
 * Created by aladin on 2019. 5. 13..
 */
@RepositoryEventHandler(WorkBook.class)
public class WorkBookEventHandler {

  @Autowired
  BookTreeService bookTreeService;
  
  @HandleBeforeCreate
  @PreAuthorize("hasPermission(#workbook, 'PERM_WORKSPACE_WRITE_BOOK')")
  public void checkCreateAuthority(WorkBook workbook) {
  }

  @HandleAfterCreate
  public void handleAfterCreate(WorkBook workbook) {
    // Tree 생성
    bookTreeService.createTree(workbook);
  }
  // 检查更新授权
  @HandleBeforeSave
  @PreAuthorize("hasPermission(#workbook, 'PERM_WORKSPACE_WRITE_BOOK')")
  public void checkUpdateAuthority(WorkBook workbook) {
  }
  // 检查删除授权
  @HandleBeforeDelete
  @PreAuthorize("hasPermission(#workbook, 'PERM_WORKSPACE_WRITE_BOOK')")
  public void checkDeleteAuthority(WorkBook workbook) {
    // Tree 삭제
    bookTreeService.deleteTree(workbook);
  }
}
