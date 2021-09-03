package com.example.boot.controller;

import com.example.boot.entity.DataBean;
import com.example.boot.repository.RequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
public class MyController {

    @Autowired
    private RequestRepository databaseDao;

    // 首页
    @RequestMapping("/")
    public String index() {
        return "Welcome to visit";
    }

    // 增
    @PostMapping("/add")
    public String add(@RequestBody DataBean addBean) {
        if (addBean == null) {
            return "{\"status\":\"300\",\"response\":\"params is null\"}";
        }
        return databaseDao.add(addBean);
    }

    // 删
    @PostMapping("/del")
    public String del(@RequestBody DataBean delBean) {
        if (delBean == null) {
            return "{\"status\":\"300\",\"response\":\"params is null\"}";
        }
        return databaseDao.del(delBean);
    }

    // 改
    @PostMapping("/modify")
    public String modify(@RequestBody DataBean modifyBean) {
        if (modifyBean == null) {
            return "{\"status\":\"300\",\"response\":\"params is null\"}";
        }
        return databaseDao.modify(modifyBean);
    }

    // 查
    @PostMapping("/query")
    public String query(@RequestBody DataBean queryBean) {
        log.info("前端参数打印：" + queryBean.toString());
        return databaseDao.query(queryBean);
    }
}