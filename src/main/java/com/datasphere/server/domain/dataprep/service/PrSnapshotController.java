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

package com.datasphere.server.domain.dataprep.service;

import com.datasphere.server.domain.dataprep.PrepDatasetStagingDbService;
import com.datasphere.server.domain.dataprep.PrepProperties;
import com.datasphere.server.domain.dataprep.PrepUtil;
import com.datasphere.server.domain.dataprep.csv.PrepCsvParseResult;
import com.datasphere.server.domain.dataprep.csv.PrepCsvUtil;
import com.datasphere.server.domain.dataprep.entity.PrSnapshot;
import com.datasphere.server.domain.dataprep.entity.PrSnapshotProjections;
import com.datasphere.server.domain.dataprep.exceptions.PrepErrorCodes;
import com.datasphere.server.domain.dataprep.exceptions.PrepException;
import com.datasphere.server.domain.dataprep.exceptions.PrepMessageKey;
import com.datasphere.server.domain.dataprep.json.PrepJsonParseResult;
import com.datasphere.server.domain.dataprep.json.PrepJsonUtil;
import com.datasphere.server.domain.dataprep.repository.PrDataflowRepository;
import com.datasphere.server.domain.dataprep.repository.PrDatasetRepository;
import com.datasphere.server.domain.dataprep.repository.PrSnapshotRepository;
import com.datasphere.server.domain.dataprep.teddy.DataFrame;
import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.hadoop.conf.Configuration;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/preparationsnapshots")
@RepositoryRestController
public class PrSnapshotController {

    private static Logger LOGGER = LoggerFactory.getLogger(PrSnapshotController.class);

    @Autowired
    private PrDatasetRepository datasetRepository;

    @Autowired
    private PrDataflowRepository dataflowRepository;

    @Autowired
    private PrSnapshotRepository snapshotRepository;

    @Autowired
    ProjectionFactory projectionFactory;

    @Autowired
    private PrSnapshotService snapshotService;

    @Autowired
    private PrepProperties prepProperties;

    @Autowired(required = false)
    private PrepDatasetStagingDbService datasetStagingDbPreviewService;

    @RequestMapping(value = "/{ssId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getSnapshot(
            @PathVariable("ssId") String ssId,
            PersistentEntityResourceAssembler persistentEntityResourceAssembler
    ) throws PrepException {
        PrSnapshot snapshot = null;
        Resource<PrSnapshotProjections.DefaultProjection> projectedSnapshot = null;
        try {
            snapshot = this.snapshotRepository.findById(ssId).get();
            if(snapshot!=null) {
            } else {
                throw PrepException.create(PrepErrorCodes.PREP_SNAPSHOT_ERROR_CODE, PrepMessageKey.MSG_DP_ALERT_NO_SNAPSHOT, ssId);
            }

            PrSnapshotProjections.DefaultProjection projection = projectionFactory.createProjection(PrSnapshotProjections.DefaultProjection.class, snapshot);
            projectedSnapshot = new Resource<>(projection);
        } catch (Exception e) {
            LOGGER.error("getSnapshot(): caught an exception: ", e);
            throw PrepException.create(PrepErrorCodes.PREP_SNAPSHOT_ERROR_CODE, e);
        }

        return ResponseEntity.status(HttpStatus.SC_OK).body(projectedSnapshot);
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public @ResponseBody
    PersistentEntityResource postSnapshot(
            @RequestBody Resource<PrSnapshot> snapshotResource,
            PersistentEntityResourceAssembler resourceAssembler
    ) throws PrepException {
        throw PrepException.create(PrepErrorCodes.PREP_SNAPSHOT_ERROR_CODE, PrepMessageKey.MSG_DP_ALERT_SNAPSHOT_SHOULD_BE_MADE_BY_TRANSFORM, "go to edit-rule");
        // Snapshot is not made by API
        /*
        PrSnapshot snapshot = null;
        PrSnapshot savedSnapshot = null;

        try {
            snapshot = snapshotResource.getContent();
            savedSnapshot = snapshotRepository.save(snapshot);
            LOGGER.debug(savedSnapshot.toString());

            this.snapshotRepository.flush();
        } catch (Exception e) {
            LOGGER.error("postSnapshot(): caught an exception: ", e);
            throw PrepException.create(PrepErrorCodes.PREP_SNAPSHOT_ERROR_CODE, PrepMessageKey.MSG_DP_ALERT_SNAPSHOT_NOT_SAVED, e.getMessage());
        }

        return resourceAssembler.toResource(savedSnapshot);
        */
    }

    @RequestMapping(value = "/{ssId}", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<?> patchSnapshot(
            @PathVariable("ssId") String ssId,
            @RequestBody Resource<PrSnapshot> snapshotResource,
            PersistentEntityResourceAssembler persistentEntityResourceAssembler
    ) throws PrepException {

        PrSnapshot snapshot = null;
        PrSnapshot patchSnapshot = null;
        PrSnapshot savedSnapshot = null;
        Resource<PrSnapshotProjections.DefaultProjection> projectedSnapshot = null;

        try {
            snapshot = this.snapshotRepository.findById(ssId).get();
            patchSnapshot = snapshotResource.getContent();

            this.snapshotService.patchAllowedOnly(snapshot, patchSnapshot);

            savedSnapshot = snapshotRepository.save(snapshot);
            LOGGER.debug(savedSnapshot.toString());

            this.snapshotRepository.flush();
        } catch (Exception e) {
            LOGGER.error("postSnapshot(): caught an exception: ", e);
            throw PrepException.create(PrepErrorCodes.PREP_SNAPSHOT_ERROR_CODE, e);
        }

        PrSnapshotProjections.DefaultProjection projection = projectionFactory.createProjection(PrSnapshotProjections.DefaultProjection.class, savedSnapshot);
        projectedSnapshot = new Resource<>(projection);
        return ResponseEntity.status(HttpStatus.SC_OK).body(projectedSnapshot);
    }

    @RequestMapping(value="/check_table/{schema}/{table}",method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> checkHiveTable(
            @PathVariable("schema") String schema,
            @PathVariable("table") String table
    ) throws PrepException {
        Map<String,Object> responseMap = new HashMap<String,Object>();
        try {
            responseMap.put("isExist",false);
        } catch (Exception e) {
            LOGGER.error("checkHiveTable(): caught an exception: ", e);
            throw PrepException.create(PrepErrorCodes.PREP_DATAFLOW_ERROR_CODE, e);
        }

        return ResponseEntity.status(HttpStatus.SC_OK).body(responseMap);
    }

    @RequestMapping(value="/{ssId}/contents",method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getContents(
            @PathVariable("ssId") String ssId,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "target", required = false, defaultValue = "1") Integer target
    ) throws PrepException {
        Map<String,Object> responseMap = new HashMap<String,Object>();
        try {
            PrSnapshot snapshot = this.snapshotRepository.findById(ssId).get();
            if(snapshot!=null) {
                DataFrame gridResponse = new DataFrame();

                PrSnapshot.SS_TYPE ss_type = snapshot.getSsType();
                if( PrSnapshot.SS_TYPE.URI==ss_type ) {
                    String storedUri = snapshot.getStoredUri();

                    String snapshotDisplayUri = snapshot.getStoredUri();
                    if(null==snapshotDisplayUri) {
                        String errorMsg = "["+ ssId + "] isn't a saved snapshot.";
                        throw PrepException.create(PrepErrorCodes.PREP_SNAPSHOT_ERROR_CODE, PrepMessageKey.MSG_DP_ALERT_SNAPSHOT_NOT_SAVED, errorMsg);
                    }

                    // We generated JSON snapshots to have ".json" at the end of the URI.
                    Configuration hadoopConf = PrepUtil.getHadoopConf(prepProperties.getHadoopConfDir(false));
                    if (storedUri.endsWith(".json")) {
                        PrepJsonParseResult result = PrepJsonUtil.parseJson(snapshot.getStoredUri(), 10000, null, hadoopConf);
                        gridResponse.setByGridWithJson(result);
                    } else {
                        PrepCsvParseResult result = PrepCsvUtil.parse(snapshot.getStoredUri(), ",", 10000, null, hadoopConf, true);
                        gridResponse.setByGrid(result);
                    }

                    responseMap.put("offset", gridResponse.rows.size());
                    responseMap.put("size", gridResponse.rows.size());
                    responseMap.put("gridResponse", gridResponse);
                } else if( PrSnapshot.SS_TYPE.STAGING_DB==ss_type ) {
                    String dbName = snapshot.getDbName();
                    String tblName = snapshot.getTblName();
                    String sql = "SELECT * FROM "+dbName+"."+tblName;
                    Integer size = offset+target;
                    gridResponse = datasetStagingDbPreviewService.getPreviewStagedbForDataFrame(sql,dbName,tblName,String.valueOf(size));
                    if(null==gridResponse) {
                        gridResponse = new DataFrame();
                    } else {
                        int rowSize = gridResponse.rows.size();
                        int lastIdx = offset + target -1;
                        if(0<=lastIdx && offset<rowSize) {
                            if(rowSize<=lastIdx) {
                                lastIdx = rowSize;
                            }
                            gridResponse.rows = gridResponse.rows.subList(offset,lastIdx);
                        } else {
                            gridResponse.rows.clear();
                        }
                    }
                    Integer gridRowSize = gridResponse.rows.size();
                    responseMap.put("offset", offset+gridRowSize);
                    responseMap.put("size", gridRowSize);
                    responseMap.put("gridResponse", gridResponse);
                }

                // We put the info below as attributes of the entity
//                Map<String,Object> originDs = Maps.newHashMap();
//                String dsName = snapshot.getOrigDsInfo("dsName");
//                String queryStmt = snapshot.getOrigDsInfo("queryStmt");
//                String filePath = snapshot.getOrigDsInfo("filePath");
//                String createdTime = snapshot.getOrigDsInfo("createdTime");
//                originDs.put("dsName",dsName);
//                originDs.put("qryStmt",queryStmt);
//                originDs.put("filePath",filePath);
//                originDs.put("createdTime",createdTime);
//                responseMap.put("originDsInfo", originDs);
            } else {
                String errorMsg = "snapshot["+ssId+"] does not exist";
                throw PrepException.create(PrepErrorCodes.PREP_SNAPSHOT_ERROR_CODE, PrepMessageKey.MSG_DP_ALERT_NO_SNAPSHOT, errorMsg);
            }
        } catch (Exception e) {
            LOGGER.error("getContents(): caught an exception: ", e);
            throw PrepException.create(PrepErrorCodes.PREP_SNAPSHOT_ERROR_CODE, e);
        }

        return ResponseEntity.status(HttpStatus.SC_OK).body(responseMap);
    }

    @RequestMapping(value="/{ssId}/download",method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getDownload(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable("ssId") String ssId,
            @RequestParam(value = "fileType", required = false, defaultValue = "0") String fileType
    ) throws PrepException {
        try {
            String downloadFileName = this.snapshotService.downloadSnapshotFile(ssId, response, fileType);
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", downloadFileName));
        } catch (Exception e) {
            LOGGER.error("getDownload(): caught an exception: ", e);
            throw PrepException.create(PrepErrorCodes.PREP_TRANSFORM_ERROR_CODE, e);
        }

        return null;
    }

    @RequestMapping(value="/{dsId}/work_list",method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> workList(
            @PathVariable("dsId") String dsId,
            @RequestParam(value = "option", required = false, defaultValue = "NOT_ALL") String option) throws PrepException {
        Map<String, Object> response = Maps.newHashMap();
        try {
            List<PrSnapshot> snapshots = this.snapshotService.getWorkList(dsId, option);
            response.put("snapshots",snapshots);
        } catch (Exception e) {
            LOGGER.error("workList(): caught an exception: ", e);
            throw PrepException.create(PrepErrorCodes.PREP_SNAPSHOT_ERROR_CODE,e);
        }
        return ResponseEntity.ok(response);
    }
}

