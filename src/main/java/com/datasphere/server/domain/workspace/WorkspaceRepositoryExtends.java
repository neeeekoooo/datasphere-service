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

package com.datasphere.server.domain.workspace;

import java.util.List;
import java.util.Map;

public interface WorkspaceRepositoryExtends {

  /**
   *
   * 通过链接的数据源来查找工作空间
   * @param dataSourceId
   * @param publicType
   * @param nameContains
   * @return
   */
  List<String> findWorkspaceIdByLinkedDataSource(String dataSourceId,
                                                        Workspace.PublicType publicType,
                                                        String nameContains);

  /**
   * 通过链接的连接来查找工作空间
   * @param connectionId
   * @param publicType
   * @param nameContains
   * @return
   */
  List<String> findWorkspaceIdByLinkedConnection(String connectionId,
                                                 Workspace.PublicType publicType,
                                                 String nameContains);

  /**
   *
   * @param username
   * @param includeRole
   * @return
   */
  List<String> findMyWorkspaceIds(String username, List<String> memberIds, String... includeRole);
  // 通过权限来查找工作空间
  List<String> findMyWorkspaceIdsByPermission(String username, List<String> memberIds, String... permissions);

  Map<String, Long> countByBookType(Workspace workspace);
  // 通过成员类型来计数
  Map<WorkspaceMember.MemberType, Long> countByMemberType(Workspace workspace);
  // 通过工作表来计算仪表盘
  Double avgDashBoardByWorkBook(Workspace workspace);
}
