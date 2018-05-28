package com.timer.database.bean;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.stereotype.Component;

@Component
public class DSimpleJob implements SimpleJob {
    public void execute(ShardingContext shardingContext) {
        System.out.println(shardingContext.getShardingItem());
    }
}
