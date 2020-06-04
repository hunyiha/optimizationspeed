package com.example.generator.controller;

import com.example.generator.po.User;
import com.example.generator.service.UserService;
import com.example.generator.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: optimizationspeed->UserController
 * @description:
 * @author: hunyiha
 * @create: 2020-06-04 20:43
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public List<User> listUser(){
        return userService.listUser();
    }

    @RequestMapping("/update")
    public boolean updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @RequestMapping("/idLessThanOrEqual/{id}")
    public List<Integer> idList(@PathVariable("id") Integer id){
        return userService.idLessThanOrEqualList(id);
    }

    @RequestMapping("/mul")
    public List<UserVo> getListUserVo(){
        return userService.getListUserVo();
    }

}
