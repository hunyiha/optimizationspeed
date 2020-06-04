package com.example.generator.service;

import com.example.generator.po.User;
import com.example.generator.vo.UserVo;

import java.util.List;

public interface UserService {

    List<User> listUser();

    boolean updateUser(User user);

    List<Integer> idLessThanOrEqualList(Integer id);

    List<UserVo> getListUserVo();

    List<Integer> idList();

    User selectUserById(Integer id);
}
