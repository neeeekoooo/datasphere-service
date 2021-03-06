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

//import javax.persistence.Column;
//import javax.persistence.*;
import java.util.Date;

//@Table(name = "T_OPERATE_CODE")
public class OperateCode {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_OPERATE_CODE.ID
     *
     * @mbg.generated
     */
//    @Id
    //@Column(name = "ID")
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_OPERATE_CODE.OPERATE_CODE
     *
     * @mbg.generated
     */
    //@Column(name = "OPERATE_CODE")
    private String operateCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_OPERATE_CODE.NAME
     *
     * @mbg.generated
     */
    //@Column(name = "NAME")
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_OPERATE_CODE.TYPE
     *
     * @mbg.generated
     */
    //@Column(name = "TYPE")
    private String type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_OPERATE_CODE.UPDATE_TIME
     *
     * @mbg.generated
     */
    //@Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_OPERATE_CODE.CREATE_TIME
     *
     * @mbg.generated
     */
    //@Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_OPERATE_CODE
     *
     * @mbg.generated
     */
    public OperateCode(Integer id, String operateCode, String name, String type, Date updateTime, Date createTime) {
        this.id = id;
        this.operateCode = operateCode;
        this.name = name;
        this.type = type;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    public OperateCode(String operateCode, String name, String type) {
        this.operateCode = operateCode;
        this.name = name;
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_OPERATE_CODE
     *
     * @mbg.generated
     */
    public OperateCode() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_OPERATE_CODE.ID
     *
     * @return the value of T_OPERATE_CODE.ID
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_OPERATE_CODE.ID
     *
     * @param id the value for T_OPERATE_CODE.ID
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_OPERATE_CODE.OPERATE_CODE
     *
     * @return the value of T_OPERATE_CODE.OPERATE_CODE
     *
     * @mbg.generated
     */
    public String getOperateCode() {
        return operateCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_OPERATE_CODE.OPERATE_CODE
     *
     * @param operateCode the value for T_OPERATE_CODE.OPERATE_CODE
     *
     * @mbg.generated
     */
    public void setOperateCode(String operateCode) {
        this.operateCode = operateCode == null ? null : operateCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_OPERATE_CODE.NAME
     *
     * @return the value of T_OPERATE_CODE.NAME
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_OPERATE_CODE.NAME
     *
     * @param name the value for T_OPERATE_CODE.NAME
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_OPERATE_CODE.TYPE
     *
     * @return the value of T_OPERATE_CODE.TYPE
     *
     * @mbg.generated
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_OPERATE_CODE.TYPE
     *
     * @param type the value for T_OPERATE_CODE.TYPE
     *
     * @mbg.generated
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_OPERATE_CODE.UPDATE_TIME
     *
     * @return the value of T_OPERATE_CODE.UPDATE_TIME
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_OPERATE_CODE.UPDATE_TIME
     *
     * @param updateTime the value for T_OPERATE_CODE.UPDATE_TIME
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_OPERATE_CODE.CREATE_TIME
     *
     * @return the value of T_OPERATE_CODE.CREATE_TIME
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_OPERATE_CODE.CREATE_TIME
     *
     * @param createTime the value for T_OPERATE_CODE.CREATE_TIME
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}