package com.timer.database.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.timer.database.configuration.StringAlias;
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
    public void changeStrategy(String name,boolean isInitial) throws Exception {
        List<Map<String, Object>> result = jdbcTemplate.queryForList(querySql);
        if (result.isEmpty())
            throw new Exception("there is no data of company names in database");
        String index = (String) result.get(0).get("names");
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(index.split(",")));
        if(isInitial || !list.contains(name)) {
            shardingForDataStrategy.shard(list);
        }
        System.out.println(ShardingForDataStrategy.sharding);
        if(!isInitial && !list.contains(name))
            index = index+','+name;
            jdbcTemplate.update("update sharding values(-1,index) where id = -1");
    }

}
