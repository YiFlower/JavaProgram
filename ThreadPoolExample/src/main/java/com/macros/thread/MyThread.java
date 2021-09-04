package com.macros.thread;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.Callable;

/**
 * need：
 * 1. 实现多线程的类
 * 2. 内置多线程
 * 3. 传参
 */
public class MyThread implements Callable<ResultSet> {
    private int i;
    private Connection connection;
    private String sql;


    public MyThread(int i, Connection connection, String sql) {
        this.i = i;
        this.connection = connection;
        this.sql = sql;
    }

    /**
     * 如果要接受参数，考虑使用构造方法
     */
    @Override
    public ResultSet call() throws Exception {
        String name = Thread.currentThread().getName();
        Statement statements = this.connection.createStatement();
        System.out.println("线程：--->" + name);
        return statements.executeQuery(sql);
    }

    @Override
    public String toString() {
        return "MyThreadPool{" +
                "connection=" + connection +
                ", sql='" + sql + '\'' +
                '}';
    }
}
