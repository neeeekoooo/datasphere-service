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

package com.datasphere.server.common.criteria;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ListCriterion {

  ListCriterionKey criterionKey;
  ListCriterionType criterionType;
  String criterionName;
  List<ListCriterion> subCriteria;
  List<ListFilter> filters;
  Boolean searchable;

  public ListCriterion(){
  }

  public ListCriterion(ListCriterionKey criterionKey, ListCriterionType criterionType, String criterionName){
    this.criterionKey = criterionKey;
    this.criterionType = criterionType;
    this.criterionName = criterionName;
  }

  public ListCriterion(ListCriterionKey criterionKey, ListCriterionType criterionType, String criterionName, Boolean searchable){
    this.criterionKey = criterionKey;
    this.criterionType = criterionType;
    this.criterionName = criterionName;
    this.searchable = searchable;
  }

  public void addFilter(ListFilter filter){
    if(filters == null){
      filters = new ArrayList<>();
    }
    filters.add(filter);
  }

  public void addSubCriterion(ListCriterion subCriterion){
    if(subCriteria == null){
      subCriteria = new ArrayList<>();
    }
    subCriteria.add(subCriterion);
  }

  public ListCriterionKey getCriterionKey() {
    return criterionKey;
  }

  public void setCriterionKey(ListCriterionKey criterionKey) {
    this.criterionKey = criterionKey;
  }

  public ListCriterionType getCriterionType() {
    return criterionType;
  }

  public void setCriterionType(ListCriterionType criterionType) {
    this.criterionType = criterionType;
  }

  public String getCriterionName() {
    return criterionName;
  }

  public void setCriterionName(String criterionName) {
    this.criterionName = criterionName;
  }

  public List<ListCriterion> getSubCriteria() {
    return subCriteria;
  }

  public void setSubCriteria(List<ListCriterion> subCriteria) {
    this.subCriteria = subCriteria;
  }

  public List<ListFilter> getFilters() {
    return filters;
  }

  public void setFilters(List<ListFilter> filters) {
    this.filters = filters;
  }

  public Boolean getSearchable() {
    return searchable;
  }

  public void setSearchable(Boolean searchable) {
    this.searchable = searchable;
  }
}
