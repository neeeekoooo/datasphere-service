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

package com.datasphere.server.query.druid.queries;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import com.datasphere.server.datasource.data.CandidateQueryRequest;
import com.datasphere.server.domain.workbook.configurations.datasource.DataSource;
import com.datasphere.server.domain.workbook.configurations.field.DimensionField;
import com.datasphere.server.domain.workbook.configurations.field.Field;
import com.datasphere.server.domain.workbook.configurations.field.UserDefinedField;
import com.datasphere.server.query.druid.AbstractQueryBuilder;
import com.datasphere.server.query.druid.Dimension;
import com.datasphere.server.query.druid.Granularity;
import com.datasphere.server.query.druid.SearchQuerySpec;
import com.datasphere.server.query.druid.dimensions.DefaultDimension;
import com.datasphere.server.query.druid.dimensions.LookupDimension;
import com.datasphere.server.query.druid.filters.AndFilter;
import com.datasphere.server.query.druid.granularities.SimpleGranularity;
import com.datasphere.server.query.druid.lookup.MapLookupExtractor;
import com.datasphere.server.query.druid.searches.AllSearchQuerySpec;
import com.datasphere.server.query.druid.searches.FragmentSearchQuerySpec;
import com.datasphere.server.query.druid.searches.InsensitiveContainsSearchQuerySpec;
import com.datasphere.server.query.druid.sorts.SearchHitSort;
import com.datasphere.server.query.druid.virtualcolumns.IndexVirtualColumn;
import com.datasphere.server.query.druid.virtualcolumns.VirtualColumn;

import static com.datasphere.server.domain.workbook.configurations.field.Field.FIELD_NAMESPACE_SEP;

/**
 *
 */
public class SearchQueryBuilder extends AbstractQueryBuilder {

  private final Logger LOGGER = LoggerFactory.getLogger(SearchQueryBuilder.class);

  private AndFilter filter = new AndFilter();

  private List<Dimension> dimensions = Lists.newArrayList();

  private SearchQuerySpec query;

  private Granularity granularity;

  private Integer limit = 1000;

  private List<String> intervals = Lists.newArrayList();

  private SearchHitSort sort;

  public SearchQueryBuilder(DataSource dataSource) {
    super(dataSource);
  }

  public SearchQueryBuilder initVirtualColumns(List<UserDefinedField> userFields) {

    setVirtualColumns(userFields);

    return this;
  }

  public SearchQueryBuilder fields(List<Field> reqFields) {

    Preconditions.checkArgument(CollectionUtils.isNotEmpty(reqFields), "Required fields.");

    for (Field field : reqFields) {

      String fieldName = checkColumnName(field.getColunm());
      String engineColumnName = engineColumnName(fieldName);

      if (!fieldName.equals(field.getColunm())) {
        field.setRef(StringUtils.substringBeforeLast(fieldName, FIELD_NAMESPACE_SEP));
      }

      String alias = field.getAlias();

      if (field instanceof DimensionField) {

        if (virtualColumns.containsKey(fieldName)) {          // from virtual column
          VirtualColumn virtualColumn = virtualColumns.get(fieldName);
          if (virtualColumn instanceof IndexVirtualColumn) {
            dimensions.add(new DefaultDimension(((IndexVirtualColumn) virtualColumn).getKeyDimension(), alias));
          } else {
            dimensions.add(new DefaultDimension(engineColumnName, alias));
          }
          unUsedVirtualColumnName.remove(fieldName);
        } else if (metaFieldMap.containsKey(fieldName)) {     // from datasource
          // ValueAlias 처리
          if (MapUtils.isNotEmpty(field.getValuePair())) {
            dimensions.add(new LookupDimension(engineColumnName,
                                               alias,
                                               new MapLookupExtractor(field.getValuePair())));
          } else {
            dimensions.add(new DefaultDimension(engineColumnName, alias));
          }
        } else {
          LOGGER.debug("Unusable dimension : {}", fieldName);
        }
      }
    }

    return this;
  }

  public SearchQueryBuilder filters(List<com.datasphere.server.domain.workbook.configurations.filter.Filter> reqFilters) {

    extractPartitions(reqFilters);

    setFilters(filter, reqFilters, intervals);

    return this;
  }


  public SearchQueryBuilder limit(Integer reqLimit) {
    if (reqLimit != null) {
      limit = reqLimit;
    }
    return this;
  }

  public SearchQueryBuilder sort(CandidateQueryRequest.SortCreteria sortCreteria) {
    sort = SearchHitSort.searchSort(sortCreteria);
    return this;
  }

  public SearchQueryBuilder query(String searchWord) {

    if (StringUtils.isEmpty(searchWord)) {
      query = new AllSearchQuerySpec();
      return this;
    }

    String[] splitedWords = StringUtils.split(searchWord, " ");
    if (splitedWords.length > 1) {
      query = new FragmentSearchQuerySpec(false, Lists.newArrayList(splitedWords));
    } else if (splitedWords.length == 1) {
      query = new InsensitiveContainsSearchQuerySpec(splitedWords[0]);
    } else {
      query = new AllSearchQuerySpec();
    }

    return this;
  }

  public SearchQuery build() {

    SearchQuery searchQuery = new SearchQuery();

    searchQuery.setDataSource(getDataSourceSpec(dataSource));
    searchQuery.setSearchDimensions(dimensions);

    if (filter != null && CollectionUtils.isEmpty(filter.getFields())) {
      searchQuery.setFilter(null);
    } else {
      searchQuery.setFilter(filter);
    }

    if (MapUtils.isNotEmpty(virtualColumns)) {
      // 먼저, 사용하지 않는 사용자 정의 컬럼 삭제
      for (String removeColumnName : unUsedVirtualColumnName) {
        virtualColumns.remove(removeColumnName);
      }
      searchQuery.setVirtualColumns(Lists.newArrayList(virtualColumns.values()));
    }

    searchQuery.setGranularity(new SimpleGranularity("all"));

    if (CollectionUtils.isEmpty(intervals)) {
      searchQuery.setIntervals(DEFAULT_INTERVALS);
    } else {
      searchQuery.setIntervals(intervals);
    }

    searchQuery.setQuery(query);

    if (sort != null) {
      searchQuery.setSort(sort);
    }

    searchQuery.setLimit(limit);

    if (StringUtils.isNotEmpty(queryId)) {
      addQueryId(queryId);
    }

    if (context != null) {
      searchQuery.setContext(context);
    }

    return searchQuery;

  }

}
