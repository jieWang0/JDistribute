package com.timer.database.bean;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.util.List;

public class DDataflowJob implements DataflowJob {
    public List fetchData(ShardingContext shardingContext) {
        return null;
    }

    public void processData(ShardingContext shardingContext, List list) {

    }
}
