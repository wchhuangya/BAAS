package com.ch.wchhuangya.baas.bean;

import cn.bmob.v3.BmobObject;

/**
 * 测试数据操作 Bean
 * Created by wchya on 16/9/14.
 */
public class Person extends BmobObject {
    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
