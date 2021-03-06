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

package com.datasphere.server.query.druid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.datasphere.server.common.datasource.LogicalType;
import com.datasphere.server.query.druid.dimensions.DefaultDimension;
import com.datasphere.server.query.druid.dimensions.ExpressionDimension;
import com.datasphere.server.query.druid.dimensions.ExtractionDimension;
import com.datasphere.server.query.druid.dimensions.LookupDimension;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.EXTERNAL_PROPERTY, property="type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = DefaultDimension.class, name = "default"),
    @JsonSubTypes.Type(value = LookupDimension.class, name = "lookup"),
    @JsonSubTypes.Type(value = ExpressionDimension.class, name = "expression"),
    @JsonSubTypes.Type(value = ExtractionDimension.class, name = "extraction")
})
public interface Dimension {

  String getDimension();

  String getOutputName();

  @JsonIgnore
  LogicalType getLogicalType();

}
