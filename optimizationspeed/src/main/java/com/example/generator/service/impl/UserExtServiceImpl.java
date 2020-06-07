package com.example.generator.service.impl;

import com.example.generator.po.User;
import com.example.generator.service.UserExtService;
import com.example.generator.service.UserService;
import com.example.generator.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: optimizationspeed->UserExtServiceImpl
 * @description:
 * @author: hunyiha
 * @create: 2020-06-07 21:42
 **/

@Service
public class UserExtServiceImpl implements UserExtService {

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Autowired
    private UserService userService;

    @Override
    public List<UserVo> userExtList() {
        List<UserVo> listUserVo = new ArrayList<>();
        List<Integer> ids = userService.idList();
        CountDownLatch countDownLatch = new CountDownLatch(ids.size());
        for (Integer id : ids) {
            executorService.execute(()->{
                UserVo userVoInfo = userService.getUserVoInfo(id);
                listUserVo.add(userVoInfo);
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Collections.sort(listUserVo,(o1, o2)->{return o1.getId()-o2.getId();});
        return listUserVo;
    }
}
