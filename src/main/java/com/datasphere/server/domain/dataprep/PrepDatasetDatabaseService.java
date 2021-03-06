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

package com.datasphere.server.domain.dataprep;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.datasphere.engine.datasource.connections.jdbc.accessor.JdbcAccessor;
import com.datasphere.engine.datasource.connections.jdbc.dialect.JdbcDialect;
import com.datasphere.engine.datasource.connections.jdbc.exception.JdbcDataConnectionException;
import com.datasphere.server.domain.dataconnection.DataConnection;
import com.datasphere.server.domain.dataconnection.DataConnectionHelper;
import com.datasphere.server.domain.dataconnection.DataConnectionRepository;
import com.datasphere.server.domain.dataprep.entity.PrDataset;
import com.datasphere.server.domain.dataprep.entity.PrDataset.RS_TYPE;
import com.datasphere.server.domain.dataprep.jdbc.PrepJdbcService;
import com.datasphere.server.domain.dataprep.repository.PrDatasetRepository;
import com.datasphere.server.domain.dataprep.service.PrDatasetService;
import com.datasphere.server.domain.dataprep.teddy.DataFrame;
import com.datasphere.server.domain.dataprep.transform.TeddyImpl;
import com.google.common.collect.Sets;

@Service
public class PrepDatasetDatabaseService {

  private static Logger LOGGER = LoggerFactory.getLogger(PrepDatasetDatabaseService.class);

  @Autowired
  PrDatasetRepository datasetRepository;

  @Autowired
  PrDatasetService datasetService;

  @Autowired
  TeddyImpl teddyImpl;

  ExecutorService poolExecutorService = null;
  Set<Future<Integer>> futures = null;

  public class PrepDatasetTotalLinesCallable implements Callable {

    PrDatasetRepository datasetRepository;

    PrDataset dataset;

    String sql;
    String connectUrl;
    String username;
    String password;
    String dbName;

    public PrepDatasetTotalLinesCallable(PrDatasetRepository datasetRepository, PrDataset dataset,
        String sql, String connectUrl, String username, String password, String dbName) {
      this.datasetRepository = datasetRepository;
      this.dataset = dataset;
      this.sql = sql;
      this.connectUrl = connectUrl;
      this.username = username;
      this.password = password;
      this.dbName = dbName;
    }

    public Integer call() {
      Integer totalLines = 0;
      try {
        Thread.sleep(500);

        Connection conn = DriverManager.getConnection(connectUrl, username, password);
        if (conn != null) {
          if (dbName != null && false == dbName.isEmpty()) {
            conn.setCatalog(dbName);
          }

          Statement statement = conn.createStatement();
          ResultSet rs = statement.executeQuery("SELECT count(*) from (" + sql + ") AS query_stmt");
          while (rs.next()) {
            totalLines = rs.getInt(1);
            break;
          }

          JdbcUtils.closeResultSet(rs);
          JdbcUtils.closeStatement(statement);
          JdbcUtils.closeConnection(conn);
        }

        if (totalLines != null) {
          dataset.setTotalLines(totalLines.longValue());
          datasetRepository.saveAndFlush(dataset);
        }
      } catch (ObjectOptimisticLockingFailureException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return totalLines;
    }
  }

  public PrepDatasetDatabaseService() {
    this.poolExecutorService = Executors.newCachedThreadPool();
    this.futures = Sets.newHashSet();
  }

  @Autowired(required = false)
  DataConnectionRepository connectionRepository;

  private void countTotalLines(PrDataset dataset, DataConnection dataConnection) throws JdbcDataConnectionException {
    JdbcAccessor jdbcDataAccessor = DataConnectionHelper.getAccessor(dataConnection);
    JdbcDialect dialect = jdbcDataAccessor.getDialect();

    String connectUrl = dialect.getConnectUrl(dataConnection);
    String username = dataConnection.getUsername();
    String password = dataConnection.getPassword();
    String queryStmt = dataset.getQueryStmt();
    String tblName = dataset.getTblName();
    String dbName = dataset.getDbName();

    dataset.setTotalLines(-1L);
    dataset.setTotalBytes(-1L);
    datasetRepository.saveAndFlush(dataset);

    Callable<Integer> callable = new PrepDatasetTotalLinesCallable(datasetRepository, dataset,
        queryStmt, connectUrl, username, password, dbName);
    this.futures.add(poolExecutorService.submit(callable));
  }

  public DataFrame getPreviewLinesFromJdbcForDataFrame(PrDataset dataset, String size) throws JdbcDataConnectionException {
    DataFrame dataFrame;
    String dcId = dataset.getDcId();
    DataConnection dataConnection = this.datasetService.findRealDataConnection(this.connectionRepository.findById(dcId).get());
    PrepJdbcService connectionService = new PrepJdbcService();

    if (dataConnection instanceof DataConnection == false) {
      dataConnection = connectionService.makeJdbcDataConnection(dataConnection);
    }

    int limit = Integer.parseInt(size);
    String sql = dataset.getRsType() == RS_TYPE.QUERY ? dataset.getQueryStmt() :
        String.format("SELECT * FROM %s.%s", dataset.getDbName(), dataset.getTblName());

    dataFrame = teddyImpl.loadJdbcDataFrame(dataConnection, sql, limit, null);

    countTotalLines(dataset, dataConnection);
    return dataFrame;
  }
}

