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

package com.datasphere.server.spec.druid.ingestion.firehose;

import com.google.common.collect.Lists;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Created by aladin on 2019. 6. 18..
 */
public class CombiningFirehose implements Firehose {

  @NotNull
  List<Firehose> delegates;

  public CombiningFirehose() {
  }

  public CombiningFirehose(Firehose... delegates) {
    this.delegates = Lists.newArrayList(delegates);
  }

  public List<Firehose> getDelegates() {
    return delegates;
  }

  public void setDelegates(List<Firehose> delegates) {
    this.delegates = delegates;
  }
}
