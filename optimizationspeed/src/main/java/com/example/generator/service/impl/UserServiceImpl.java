package com.example.generator.service.impl;

import com.example.generator.dao.UserMapper;
import com.example.generator.po.User;
import com.example.generator.po.UserExample;
import com.example.generator.service.UserService;
import com.example.generator.vo.UserVo;
import com.sun.jmx.snmp.tasks.ThreadService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @program: optimizationspeed->UserServiceImpl
 * @description:
 * @author: hunyiha
 * @create: 2020-06-04 20:46
 **/

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    @Override
    public List<User> listUser() {
        UserExample example = new UserExample();
        example.createCriteria().andIdIsNotNull();
        return userMapper.selectByExample(example);
    }

    @Override
    public boolean updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user)==1 ? true : false;
    }


    @Override
    public List<Integer> idLessThanOrEqualList(Integer id){
        UserExample example = new UserExample();
        example.createCriteria().andIdLessThanOrEqualTo(id);
        return userMapper.selectByExample(example).stream().map(User::getId).collect(Collectors.toList());
    }



    @Override
    public List<UserVo> getListUserVo(){
        List<Integer> ids = idList();
        List<UserVo> listUserVo = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(ids.size());
        for (Integer id : ids) {
            executorService.execute(()->{
                UserVo userVoInfo = getUserVoInfo(id);
                listUserVo.add(userVoInfo);
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Collections.sort(listUserVo,(o1,o2)->{return o1.getId()-o2.getId();});
        return listUserVo;
    }

    @Override
    public List<Integer> idList() {
        UserExample example = new UserExample();
        example.createCriteria().andIdIsNotNull();
        return userMapper.selectByExample(example).stream().map(User::getId).collect(Collectors.toList());
    }

    @Override
    public User selectUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public UserVo getUserVoInfo(Integer id) {
        List<User> users = listUser();
        User userById = selectUserById(id);
        UserVo userVo = new UserVo();

        for (User user : users) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            userVo.setAgeNum(userVo.getAgeNum() + user.getAge());
        }
       /* final CountDownLatch latch= new CountDownLatch(users.size());//使用java并发库concurrent
        for (User user : users) {
            executorService.submit(()->{
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (this){
                        userVo.setAgeNum(userVo.getAgeNum() + user.getAge());
                    }
                    latch.countDown();
            });
        }*/
       /* try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
            BeanUtils.copyProperties(userById, userVo);
            return userVo;
    }


    @Override
    public List<UserVo> getListUserVo1(){
        List<Integer> ids = idList();
        List<UserVo> listUserVo = new ArrayList<>();
        for (Integer id : ids) {
                UserVo userVoInfo = getUserVoInfo(id);
                listUserVo.add(userVoInfo);
        }
        return listUserVo;
    }
}
