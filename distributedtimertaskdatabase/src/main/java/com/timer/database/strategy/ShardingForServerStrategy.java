package com.timer.database.strategy;

import com.dangdang.ddframe.job.lite.api.strategy.JobShardingStrategy;
import com.dangdang.ddframe.job.lite.api.strategy.JobShardingStrategyOption;

import java.util.List;
import java.util.Map;


public class ShardingForServerStrategy implements JobShardingStrategy {

    /**
     * @param list 作业服务器IP列表
     * @param jobShardingStrategyOption 分片策略选项，
     *                                  分片策略选项包括作业名称，分片总数以及分片序列号和个性化参数对照表，可以根据需求定制化自己的分片策略。
     * */
    public Map<String, List<Integer>> sharding(List<String> list, JobShardingStrategyOption jobShardingStrategyOption) {
        return null;
    }
}
