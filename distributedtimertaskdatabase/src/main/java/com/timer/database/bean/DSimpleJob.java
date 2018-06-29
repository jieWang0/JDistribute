package com.timer.database.bean;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.timer.database.strategy.ShardingForDataStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.sql.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class DSimpleJob implements SimpleJob {

    @Value("${spring.datasource.url}")
    static String url;

    private static ExecutorService executor = Executors.newFixedThreadPool(6);
    public void execute(final ShardingContext shardingContext) {

           // if(shardingContext.getShardingItem() == 2)
                executor.execute( new Runnable() {
                    @Override
                    public void run() {
                        Connection connection = null;
                        Statement statement = null;
                        List<String> list = ShardingForDataStrategy.sharding.get(shardingContext.getShardingItem());
                        try {
                            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/timer?useUnicode=true&characterEncoding=utf-8","root","123456");
                            connection.setAutoCommit(false);
                            statement = connection.createStatement();
                            long begin = System.currentTimeMillis();
                            System.out.print(list);
                            System.out.println("---------------begin task");
                            System.out.println("当前线程ID"+Thread.currentThread().getId());
                            for(String str:list) {
                                String updateSql = "UPDATE message SET result=result-old where name = "+"'"+str+"'";
                                statement.executeUpdate(updateSql);
                            }
                            connection.commit();
                            long end = System.currentTimeMillis();
                            System.out.println("当前线程ID"+Thread.currentThread().getId());
                            System.out.println(list+"---------任务执行完毕，耗时："+(end-begin)+"ms----------");
                            executor.execute(new CommitData(String.valueOf(begin),String.valueOf(end)));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                statement.close();
                                connection.close();
                            } catch (SQLException e) {
                            }
                        }
                    }
                });
        }


        /**
         * Connection的三个方法与事务有关：
         *
         * setAutoCommit（boolean）:设置是否为自动提交事务，如果true（默认值为true）表示自动提交，也就是每条执行的SQL语句都是一个单独的事务，如果设置为false，那么相当于开启了事务了；con.setAutoCommit(false) 表示开启事务。
         * commit（）：提交结束事务。
         * rollback（）：回滚结束事务。
         * */
}
