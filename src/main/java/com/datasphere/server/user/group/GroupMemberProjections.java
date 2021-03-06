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

package com.datasphere.server.user.group;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.datasphere.server.domain.user.UserProfile;

/**
 *
 */
public class GroupMemberProjections {

  @Projection(name = "default", types = { GroupMember.class })
  public interface DefaultProjection {

    String getMemberId();

    String getMemberName();

  }

  @Projection(name = "forListView", types = { GroupMember.class })
  public interface ForListViewProjection {

    String getMemberId();

    String getMemberName();

    // TODO: 최적화된 방안 찾아볼것!
    @Value("#{@cachedUserService.findUserProfile(target.getMemberId())}")
    UserProfile getProfile();

  }

  @Projection(name = "forDetailView", types = { GroupMember.class })
  public interface ForDetailViewProjection {

    String getMemberId();

    String getMemberName();

    @Value("#{@cachedUserService.findUserProfile(target.getMemberId())}")
    UserProfile getProfile();
  }

}
