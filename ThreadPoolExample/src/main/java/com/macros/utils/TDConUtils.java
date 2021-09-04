package com.macros.utils;


import com.taosdata.jdbc.TSDBDriver;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class TDConUtils {
    private static Logger log = Logger.getLogger(TDConUtils.class);

    public Connection getCon() throws ClassNotFoundException, SQLException {
        Class.forName("com.taosdata.jdbc.TSDBDriver");
        String connectUrl = "jdbc:TAOS://192.168.1.60:6030/log?user=root&password=taosdata";

//        Class.forName("com.taosdata.jdbc.rs.RestfulDriver");
//        String connectUrl = "jdbc:TAOS-RS://10.168.1.13:6041/log?user=root&password=taosdata";
        Properties properties = new Properties();
        properties.setProperty(TSDBDriver.PROPERTY_KEY_CHARSET, "UTF-8");
        properties.setProperty(TSDBDriver.PROPERTY_KEY_LOCALE, "en_US.UTF-8");
        properties.setProperty(TSDBDriver.PROPERTY_KEY_TIME_ZONE, "UTC-8");

        // getCon
        return DriverManager.getConnection(connectUrl, properties);
    }

    // 关闭资源
    public void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void close(Connection connection, Statement statement) {
        close(connection);
        close(statement);
    }
}
