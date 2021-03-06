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

package com.datasphere.server.user.service;

import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import com.datasphere.server.user.DirectoryProfile;
import com.datasphere.server.user.User;
import com.datasphere.server.user.UserProfile;
import com.datasphere.server.user.UserRepository;
import com.datasphere.server.user.group.Group;
import com.datasphere.server.user.group.GroupProfile;
import com.datasphere.server.user.group.GroupRepository;
import com.datasphere.server.user.role.RoleService;
import com.datasphere.server.domain.workspace.WorkspaceMember;

/**
 * Created by aladin on 2019. 5. 18..
 */
@Component("cachedUserService")
public class CachedUserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(InnerUserServiceImpl.class);

  private static final Map<String, User> userMap = Maps.newHashMap();

  private static final Map<String, Group> groupMap = Maps.newHashMap();

  @Autowired
  RoleService roleService;

  @Autowired
  UserRepository userRepository;

  @Autowired
  GroupRepository groupRepository;

  public User findUser(String username) {

    if(StringUtils.isBlank(username)) {
      return null;
    }
    // 通过用户名找到用户
    if (userMap.containsKey(username)) {
      return userMap.get(username);
    } else {
      User user = userRepository.findByUsername(username);
      if (user == null) {
        LOGGER.debug("User({}) not found. Return empty User object.", username);
        return null;
      } else {
        user.setRoleService(roleService);
        userMap.put(username, user);
        return user;
      }
    }
  }
  // 删除用户缓存信息
  public void removeCachedUser(String userId) {
    if(userMap.containsKey(userId)) {
      userMap.remove(userId);
    }
  }
  // 通过群组Id找到群组
  public Group findGroup(String groupId) {

    if(groupId == null) {
      return null;
    }

    if (groupMap.containsKey(groupId)) {
      return groupMap.get(groupId);
    } else {
      Group group = groupRepository.findById(groupId).get();
      if (group == null) {
        LOGGER.debug("Role({}) not found. Return empty Role object.", groupId);
        return null;
      } else {
        groupMap.put(groupId, group);
        return group;
      }
    }
  }

  /**
   * Member Get profile information by type (user / group),
   * Projection Utilize when processing my Spel
   *
   * @param id
   * @param type
   * @return
   */
  public DirectoryProfile findProfileByMemberType(String id, WorkspaceMember.MemberType type) {
    switch (type) {
      case USER:
        return findUserProfile(id);
      case GROUP:
        return findGroupProfile(id);
    }
    return null;
  }

  /**
   * Member Get profile information by type (user / group),
   * Projection Utilize when processing my Spel
   *
   * @param id
   * @param type
   * @return
   */
  public DirectoryProfile findProfileByDirectoryType(String id, DirectoryProfile.Type type) {
    switch (type) {
      case USER:
        return findUserProfile(id);
      case GROUP:
        return findGroupProfile(id);
    }
    return null;
  }
  // 通过群组Id找到群组资料
  public GroupProfile findGroupProfile(String groupId) {
    Group group = findGroup(groupId);
    if(group == null) {
      return new GroupProfile(groupId, GroupProfile.UNKNOWN_GROUPNAME);
    } else {
      return GroupProfile.getProfile(group);
    }
  }
  // 通过用户名查找用户资料
  public UserProfile findUserProfile(String username) {
    User user = findUser(username);

    if(user == null) {
      return new UserProfile(username, UserProfile.UNKNOWN_USERNAME, null);
    } else {
      return UserProfile.getProfile(user);
    }
  }

}
