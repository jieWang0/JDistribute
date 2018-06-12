package com.timer.database.service;
import com.timer.database.strategy.ShardingForDataStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StrategyChangeService {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ShardingForDataStrategy shardingForDataStrategy;

    private static final String querySql = "select distinct names from sharding where id = -1";

    /**
     * 业务分片策略(average)
     *如name集合为[A,B,C,D],分片数为3，则对应的分片结果为0-[A,D],1-[B],2-[C]
     * @param isInitial  是否由系统初始化触发
     * @param name  每次有新的name加入时会触发重新分片，系统初始化该值为null
     * */
    public void changeStrategy(String name,boolean isInitial) throws Exception {
        List<Map<String, Object>> result = jdbcTemplate.queryForList(querySql);
        if (result.isEmpty())
            throw new Exception("there is no data of company names in database");
        String index = (String) result.get(0).get("names");
        List<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList(index.split(",")));
        if(isInitial || !list.contains(name)) {
            shardingForDataStrategy.shard(list);
        }
        System.out.println(ShardingForDataStrategy.sharding);
        if(!isInitial && !list.contains(name)) {
            index = index+','+name;
            jdbcTemplate.update("update sharding values(-1,'"+index+"') where id = -1");
        }
    }

}
