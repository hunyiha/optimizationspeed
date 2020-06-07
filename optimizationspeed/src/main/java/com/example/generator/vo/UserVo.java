package com.example.generator.vo;

import com.example.generator.po.User;

/**
 * @program: optimizationspeed->UserVo
 * @description:
 * @author: hunyiha
 * @create: 2020-06-04 21:22
 **/
public class UserVo extends User {
    private  int ageNum;

    public int getAgeNum() {
        return ageNum;
    }

    public void setAgeNum(int ageNum) {
        this.ageNum = ageNum;
    }
}
