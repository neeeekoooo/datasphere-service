package com.datasphere.engine.manager.resource.provider.controller;

import com.datasphere.core.common.BaseController;
import com.datasphere.engine.core.utils.JsonWrapper;
import com.datasphere.engine.manager.resource.provider.model.DBTableInfodmp;
import com.datasphere.engine.manager.resource.provider.service.DataSourceService;
import com.datasphere.engine.manager.resource.provider.webhdfs.model.WebHDFSConnectionInfo;
import com.datasphere.engine.manager.resource.provider.webhdfs.model.WebHDFSDataSourceInfo;

import io.reactivex.Single;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;


@Controller
public class WebHDFSDatasourceController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(WebHDFSDatasourceController.class);

    public static final String BASE_PATH = "/datasource/webhdfs";

    @Autowired
    DataSourceService dataSourceService;

    /**
     * test connection  webhdfs 
     * @param webHDFSConnectionInfo
     * @return
     */
	@PostMapping(value = BASE_PATH + "/testWebHDFS")
    public Single<Map<String,Object>> testWebHDFS(@Body WebHDFSConnectionInfo webHDFSConnectionInfo) {
        return Single.fromCallable(() -> {
            int result = dataSourceService.testWebHDFS(webHDFSConnectionInfo);
            if(result == 0){
                return JsonWrapper.failureWrapper("测试失败");
            }
            return JsonWrapper.successWrapper("测试成功");
        });
    }

    /**
     * select webhdfs list table
     * @param webHDFSConnectionInfo
     * @webhdfsConnectionInfo
     */
    @PostMapping(value = BASE_PATH + "/webHDFSListFiles")
    public Object webHDFSListFiles(@Body WebHDFSConnectionInfo webHDFSConnectionInfo){
        return Single.fromCallable(() -> {
            List<DBTableInfodmp> dbTableInfodmps = dataSourceService.webHDFSListFiles(webHDFSConnectionInfo);
            if(dbTableInfodmps == null){
                return JsonWrapper.failureWrapper("获取失败");
            }
            return JsonWrapper.successWrapper(dbTableInfodmps);
        });
    }

    /**
     * create webhdfs datasource big data
     * @param webHDFSDataSourceInfo
     * @return
     */
    @PostMapping(value = BASE_PATH + "/createWebHDFS")
    public Single<Map<String,Object>> createWebHDFS(@Body WebHDFSDataSourceInfo webHDFSDataSourceInfo){
        return Single.fromCallable(() -> {
            if(webHDFSDataSourceInfo.getBusinessType() == null){
                return JsonWrapper.failureWrapper("业务类型不能为空");
            }
            int result = dataSourceService.createWebHDFS(webHDFSDataSourceInfo);
            if (result == 0){
                return JsonWrapper.failureWrapper("插入失败");
            }
            return JsonWrapper.successWrapper("插入成功");
        });
    }

    /**
     * update WebHDFS datasource info
     * @param webHDFSDataSourceInfo
     * @return
     */
    @PostMapping(value = BASE_PATH + "/updateWebHDFS")
    public Single<Map<String,Object>> updateWebHDFS(@Body WebHDFSDataSourceInfo webHDFSDataSourceInfo){
        return Single.fromCallable(() -> {
            if(StringUtils.isBlank(webHDFSDataSourceInfo.getId())){
                return JsonWrapper.failureWrapper("id不能为空！");
            }
            //TODO 原有数据库是否被引用 -- 是否 可更新
            //TODO 查询数据库中有无数据源  验证名称是否重复

            int rsult = dataSourceService.updateWebHDFS(webHDFSDataSourceInfo);
            if(rsult == 0){
                return JsonWrapper.failureWrapper("更新失败！");
            }
            return JsonWrapper.successWrapper();
        });
    }

}
