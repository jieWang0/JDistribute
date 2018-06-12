package com.timer.database.strategy;

import com.timer.database.configuration.StringAlias;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ShardingForDataStrategy {

    /**
     * 数据分片缓存，每次服务启动会初始化,key为分片，value为分片对应的任务集合
     * */
    public static Map<Integer,List<String>> sharding = new HashMap<Integer,List<String>>();

    public void shard(List<String> names) {
            Collections.sort(names);
            int num = names.size();
             Map<Integer,List<String>> shardNew = new HashMap<Integer,List<String>>();
                while (!names.isEmpty()) {
                    for(int i =0;i<StringAlias.sharddingCount;i++) {
                        if(shardNew.containsKey(i)) {
                            shardNew.get(i).add(names.get(0));
                        }else {
                            List<String> index = new ArrayList<String>();
                            index.add(names.get(0));
                            shardNew.put(i,index);
                        }
                        names.remove(0);
                        if(names.isEmpty())
                            break;
                    }
                }
                sharding = shardNew;
            }
}
