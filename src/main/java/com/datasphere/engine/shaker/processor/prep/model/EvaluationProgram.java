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

//@Table(name = "T_EVALUATION_PROGRAMS")
public class EvaluationProgram {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_EVALUATION_PROGRAMS.ID
     *
     * @mbg.generated
     */
//    @Id
//  @Column(name = "ID")
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_EVALUATION_PROGRAMS.EVALUATION_ID
     *
     * @mbg.generated
     */
    //@Column(name = "EVALUATION_ID")
    private Integer evaluationId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_EVALUATION_PROGRAMS.PROGRAM_ID
     *
     * @mbg.generated
     */
    //@Column(name = "PROGRAM_ID")
    private Integer programId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_EVALUATION_PROGRAMS.PROGRAM_ID
     *
     * @mbg.generated
     */
    //@Column(name = "TABLE_ID")
    private String tableId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_EVALUATION_PROGRAMS.PROGRAM_ID
     *
     * @mbg.generated
     */
    //@Column(name = "STATUS")
    private String status;

    //@Column(name = "PARAMSVO")
    private String paramsvo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_EVALUATION_PROGRAMS.RESULT
     *
     * @mbg.generated
     */
    //@Column(name = "RESULT")
    private String result;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_EVALUATION_PROGRAMS.UPDATE_TIME
     *
     * @mbg.generated
     */
    //@Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_EVALUATION_PROGRAMS.CREATE_TIME
     *
     * @mbg.generated
     */
    //@Column(name = "CREATE_TIME")
    private Date createTime;

    public EvaluationProgram() {
    }

    public EvaluationProgram(Integer id, Integer evaluationId, Integer programId, String tableId,
            String status, String paramsvo, String result, Date updateTime, Date createTime) {
        this.id = id;
        this.evaluationId = evaluationId;
        this.programId = programId;
        this.tableId = tableId;
        this.status = status;
        this.paramsvo = paramsvo;
        this.result = result;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    public EvaluationProgram(Integer evaluationId, Integer programId, String tableId, String status,
            String paramsvo) {
        this.evaluationId = evaluationId;
        this.programId = programId;
        this.tableId = tableId;
        this.status = status;
        this.paramsvo = paramsvo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Integer evaluationId) {
        this.evaluationId = evaluationId;
    }

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParamsvo() {
        return paramsvo;
    }

    public void setParamsvo(String paramsvo) {
        this.paramsvo = paramsvo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override public String toString() {
        return "EvaluationProgram{" + "id=" + id + ", evaluationId=" + evaluationId + ", programId="
                + programId + ", tableId='" + tableId + '\'' + ", status='" + status + '\''
                + ", paramsvo='" + paramsvo + '\'' + ", result='" + result + '\'' + ", updateTime="
                + updateTime + ", createTime=" + createTime + '}';
    }
}
