package com.timer.database.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Configuration
public class databaseConfig {

    @Autowired
    JdbcTemplate jdbcTemplate;

    //好像@Configuration是最先加载的，所以无法使用@Value
   /* @Value("${spring.datasource.driver-class-name}")
    static String driverClassName;

    @Value("${spring.datasource.password}")
    static String passWord;

    @Value("${spring.datasource.username}")
    static String userName;

    @Value("${spring.datasource.url}")
    static String url;*/

    private final String initialCheckSql = "select message from message limit 1";
    private final String initialDatabaseSql = "insert into message(id,message,createTime) values(?,?,SYSDATE()) ";

    @Bean
    public  DriverManagerDataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setPassword("123456");
        dataSource.setUsername("root");
        dataSource.setUrl("jdbc:mysql://localhost:3306/timer?useUnicode=true&characterEncoding=utf-8");
        return dataSource;
    }

    @EventListener
    public void onEvent(ApplicationContextEvent event) {
        List<Map<String, Object>> result = jdbcTemplate.queryForList(initialCheckSql);
        if(result.isEmpty()) {
            initialDatabaseNew();
        }
    }

    //372817ms/1w
    private void initialDatabase() {
        long beginTime = new Date().getTime();
        final int total = 2*1000000;
        jdbcTemplate.batchUpdate(initialDatabaseSql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,String.valueOf(i));
                preparedStatement.setString(2,"data for test timerTask");
            }
            public int getBatchSize() {
                return total;
            }
        });
        long endTime = new Date().getTime();
        System.out.println("---------初始化数据完毕，耗时："+(endTime-beginTime)+"ms----------");
       // System.exit(0);
    }


    //266ms/1w
    private void initialDatabaseNew() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/timer?useUnicode=true&characterEncoding=utf-8","root","123456");
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            long begin = System.currentTimeMillis();
            int i =0,j=1;
            for (;j<101;j++) {
                StringBuffer sql = new StringBuffer("insert into message(id,message,createTime) values");
                for (; i < 20000*j; i++) {
                    sql.append("('" + i + "','data for test timerTask',SYSDATE()),");
                }
                sql.append("('" + i++ + "','data for test timerTask',SYSDATE())");
                statement.execute(sql.toString());
            }
            connection.commit();
            long end = System.currentTimeMillis();
            System.out.println("---------初始化数据完毕，耗时："+(end-begin)+"ms----------");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
            }
        }
    }

}
