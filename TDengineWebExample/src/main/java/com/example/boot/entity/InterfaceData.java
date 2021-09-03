package com.example.boot.entity;

import com.alibaba.fastjson.JSON;


public interface InterfaceData {
    String add(DataBean dataBean); // 增加数据

    String del(DataBean dataBean);

    String modify(DataBean dataBean);

    String query(DataBean dataBean);
}
