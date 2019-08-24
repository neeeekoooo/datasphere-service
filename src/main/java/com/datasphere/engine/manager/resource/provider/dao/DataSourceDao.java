package com.datasphere.engine.manager.resource.provider.dao;

import org.apache.ibatis.annotations.Param;

import com.datasphere.engine.manager.resource.provider.model.DataSource;
import com.datasphere.engine.manager.resource.provider.model.DataSourceWithAll;

import java.util.List;
import java.util.Map;

/**
 * 数据源管理
 */
public interface DataSourceDao {

    //获取全部数据源信息
    List<DataSource> listAll(Map<String,Object> map);

    //查询数据源条数
    Integer count(Map<String,Object> map);

    //新增一条数据源
    Integer insert(DataSource dataSource);

    //更新数据源名称及描述
    int update(DataSource dataSource);

    //根据ID查询数据源信息
    DataSource findDataSourceById(String id);

    int deleteDatasourceById(String id);

    int deleteDSByAppId(String id);

    String getDaasId(String appId);

    int getDaasIdSum(String daasId);

    int updateDatasourceById(DataSource dataSource);

    int findDatasourceByName(String s);

    List<DataSourceWithAll> getWithAll(String id, String userId);

    Integer insertDS(@Param(value = "daasId") String dassId, @Param(value = "dataSourceId") String dataSourceId , @Param(value = "daasName") String daasName);

    String getDaasName(String daasId);

    String getDaasNameByID(String id);

    String getDaasV3Catalog(String app_ds_id);

    Integer updateDSByDataSourceID(@Param(value = "daasId") String dassId, @Param(value = "dataSourceId") String dataSourceId , @Param(value = "daasName") String daasName);


}
