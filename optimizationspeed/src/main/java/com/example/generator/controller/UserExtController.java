package com.example.generator.controller;

import com.example.generator.service.UserExtService;
import com.example.generator.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: optimizationspeed->UserExtController
 * @description:
 * @author: hunyiha
 * @create: 2020-06-07 21:39
 **/
@RestController
@RequestMapping("/userext")
public class UserExtController {
    @Autowired
    private UserExtService userExtService;

    @GetMapping("/list")
    public List<UserVo> getList(){
        return userExtService.userExtList();
    }
}
