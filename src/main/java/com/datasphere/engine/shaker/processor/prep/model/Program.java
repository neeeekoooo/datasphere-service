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

package com.datasphere.engine.shaker.processor.prep.model;

import java.util.Date;

//import javax.persistence.Id;
//import javax.persistence.Table;

//@Table(name = "T_PROGRAMS")
public class Program {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_PROGRAMS.ID
     *
     * @mbg.generated
     */
//    @Id
//    @javax.persistence.Column(name = "ID")
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_PROGRAMS.NAME
     *
     * @mbg.generated
     */
//    @javax.persistence.Column(name = "NAME")
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_PROGRAMS.IS_DEFAULT
     *
     * @mbg.generated
     */
//    @javax.persistence.Column(name = "IS_DEFAULT")
    private String isDefault;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_PROGRAMS.VERSION
     *
     * @mbg.generated
     */
//    @javax.persistence.Column(name = "VERSION")
    private Integer version;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_PROGRAMS.PROCESS_ID
     *
     * @mbg.generated
     */
//    @javax.persistence.Column(name = "PROCESS_ID")
    private String processId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_PROGRAMS.TABLE_ID
     *
     * @mbg.generated
     */
//    @javax.persistence.Column(name = "TABLE_ID")
    private String tableId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_PROGRAMS.UPDATE_TIME
     *
     * @mbg.generated
     */
//    @javax.persistence.Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_PROGRAMS.CREATE_TIME
     *
     * @mbg.generated
     */
//    @javax.persistence.Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_PROGRAMS
     *
     * @mbg.generated
     */
    public Program(String name, String isDefault, Integer version, String processId, String tableId, Date updateTime, Date createTime) {
        this.name = name;
        this.isDefault = isDefault;
        this.version = version;
        this.processId = processId;
        this.tableId = tableId;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_PROGRAMS
     *
     * @mbg.generated
     */
    public Program(Integer id, String name, String isDefault, Integer version, String processId, String tableId, Date updateTime, Date createTime) {
        this.id = id;
        this.name = name;
        this.isDefault = isDefault;
        this.version = version;
        this.processId = processId;
        this.tableId = tableId;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_PROGRAMS
     *
     * @mbg.generated
     */
    public Program() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_PROGRAMS.ID
     *
     * @return the value of T_PROGRAMS.ID
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_PROGRAMS.ID
     *
     * @param id the value for T_PROGRAMS.ID
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_PROGRAMS.NAME
     *
     * @return the value of T_PROGRAMS.NAME
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_PROGRAMS.NAME
     *
     * @param name the value for T_PROGRAMS.NAME
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_PROGRAMS.IS_DEFAULT
     *
     * @return the value of T_PROGRAMS.IS_DEFAULT
     *
     * @mbg.generated
     */
    public String getIsDefault() {
        return isDefault;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_PROGRAMS.IS_DEFAULT
     *
     * @param isDefault the value for T_PROGRAMS.IS_DEFAULT
     *
     * @mbg.generated
     */
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault == null ? null : isDefault.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_PROGRAMS.VERSION
     *
     * @return the value of T_PROGRAMS.VERSION
     *
     * @mbg.generated
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_PROGRAMS.VERSION
     *
     * @param version the value for T_PROGRAMS.VERSION
     *
     * @mbg.generated
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_PROGRAMS.PROCESS_ID
     *
     * @return the value of T_PROGRAMS.PROCESS_ID
     *
     * @mbg.generated
     */
    public String getProcessId() {
        return processId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_PROGRAMS.PROCESS_ID
     *
     * @param processId the value for T_PROGRAMS.PROCESS_ID
     *
     * @mbg.generated
     */
    public void setProcessId(String processId) {
        this.processId = processId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_PROGRAMS.TABLE_ID
     *
     * @return the value of T_PROGRAMS.TABLE_ID
     *
     * @mbg.generated
     */
    public String getTableId() {
        return tableId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_PROGRAMS.TABLE_ID
     *
     * @param tableId the value for T_PROGRAMS.TABLE_ID
     *
     * @mbg.generated
     */
    public void setTableId(String tableId) {
        this.tableId = tableId == null ? null : tableId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_PROGRAMS.UPDATE_TIME
     *
     * @return the value of T_PROGRAMS.UPDATE_TIME
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_PROGRAMS.UPDATE_TIME
     *
     * @param updateTime the value for T_PROGRAMS.UPDATE_TIME
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_PROGRAMS.CREATE_TIME
     *
     * @return the value of T_PROGRAMS.CREATE_TIME
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_PROGRAMS.CREATE_TIME
     *
     * @param createTime the value for T_PROGRAMS.CREATE_TIME
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}