package com.example.boot.repository;

import com.alibaba.fastjson.JSON;
import com.example.boot.entity.DataBean;
import com.example.boot.entity.InterfaceData;
import com.taosdata.jdbc.TSDBDriver;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Properties;

@Slf4j
@Service("databaseDao")
public class RequestRepository implements InterfaceData {
    private static Connection con;
    private static Statement stmt;
    private static String driver = "com.taosdata.jdbc.rs.RestfulDriver";
    private static String url = "jdbc:TAOS-RS://10.168.1.13:6041/log?user=root&password=taosdata";

    // params
    private static String tagName;
    private static String timestamp;
    private static String valueDouble;

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Properties properties = new Properties();
        properties.setProperty(TSDBDriver.PROPERTY_KEY_CHARSET, "UTF-8");
        properties.setProperty(TSDBDriver.PROPERTY_KEY_LOCALE, "en_US.UTF-8");
        properties.setProperty(TSDBDriver.PROPERTY_KEY_TIME_ZONE, "UTC-8");
        try {
            con = DriverManager.getConnection(url, properties);
            stmt = con.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @SneakyThrows
    @Override
    public String add(DataBean dataBean) {
        return null;
    }

    @Override
    public String del(DataBean dataBean) {


        return null;
    }

    @Override
    public String modify(DataBean dataBean) {


        return null;
    }

    @Override
    public String query(DataBean dataBean) {
        String response = null;
        DataBean dataBean1 = new DataBean();
        try {
            tagName = dataBean.getTgaName();
            timestamp = dataBean.getTimestamp();
            dataBean1.setTgaName(tagName);
            dataBean1.setTimestamp(timestamp);

            //executor
            String sql = "SELECT * FROM " + tagName + " WHERE point_time = \"" + timestamp + "\"";
            log.info("Query SQL：" + sql);
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                String value_double = String.valueOf(resultSet.getDouble("value_double"));
                dataBean1.setValueDouble(value_double);

                // to String
                response = JSON.toJSONString(JSON.toJSON(dataBean1));
                log.info("返回前的打印：" + response);
            }
        } catch (Exception e) {
            return "{\"status\":\"301\",\"response\":\"" + e.getMessage() + "\"}";
        }
        return response;
    }

    public void close() {
        try {
            con.close();
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}