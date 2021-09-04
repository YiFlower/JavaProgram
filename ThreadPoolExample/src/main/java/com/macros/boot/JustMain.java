package com.macros.boot;

import com.macros.thread.MyThread;
import com.macros.utils.TDConUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.*;

public class JustMain {
    private static Logger log = Logger.getLogger(TDConUtils.class);

    private void executorServerStart() throws SQLException, ClassNotFoundException, ExecutionException, InterruptedException {
        // get con
        TDConUtils tdConUtils = new TDConUtils();
        Connection con = tdConUtils.getCon();
        Statement statement = con.createStatement();

        // SQL
        String sql = "select last_row(value_double) from SBQ.SBQFJ_NX_GD_SBQF_FJ_P2_L5_047_AI0005\n" +
                "where point_time >=1565593779000 and point_time <= 1628752179000 \n" +
                "interval(1800s) fill(prev) order by _c0 desc limit 48;";

        // ThreadPool
        ExecutorService es = Executors.newCachedThreadPool();

        // for each
        int count = 500;
        for (int i = 0; i < count; i++) {
            Future<ResultSet> submit = es.submit(new MyThread(i, con, sql));
            ResultSet resultSet = submit.get();
            // print
            while (resultSet.next()) {
                System.out.printf("输出：时间：%s,值：%f \n", resultSet.getTimestamp(1)
                        , resultSet.getDouble(2));
            }
        }

        es.shutdown();

        // close resources
        tdConUtils.close(con, statement);
    }

    /**
     * TODO: 执行查询必须要拿结果，但是ExecutorServer会阻塞线程
     * 对于ExecutorServer有两种方法，第一是轮询查询，
     * 第二种方法是将结果集写入到线程类中，使用线程类的get方法获取结果集
     * TODO：除了使用ExecutorServer，我们还可以使用CompletionServer
     */
    private void completionServerStart() throws SQLException, ClassNotFoundException, InterruptedException, ExecutionException {
        // get con
        TDConUtils tdConUtils = new TDConUtils();
        Connection con = tdConUtils.getCon();
        Statement statement = con.createStatement();

        // SQL
        String sql = "select last_row(value_double) from nss.nssfgl_nx_gd_nssf_yc_p1_l1_001_CDQ001  where point_time >='2020-01-01 00:00:00' and point_time <= '2021-07-30 19:00:00' interval(1800s) fill(prev) order by _c0 desc limit 48;";

        // ThreadPool
        ExecutorService es = Executors.newCachedThreadPool();

        //构建ExecutorCompletionService,与线程池关联
        ExecutorCompletionService<ResultSet> ecs = new ExecutorCompletionService<ResultSet>(es);

        // for each
        int count = 500;
        for (int i = 0; i < count; i++) {
            ecs.submit(new MyThread(i, con, sql));
        }

        ResultSet resultSet = ecs.take().get();
        while (resultSet.next()) {
            System.out.println(resultSet.getTimestamp("ts"));
        }

        es.shutdown();
        tdConUtils.close(con, statement);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
//        new JustMain().executorServerStart();
        new JustMain().completionServerStart();
        System.out.println("累计运行时间：" + (System.currentTimeMillis() - start) + "ms.");
    }
}