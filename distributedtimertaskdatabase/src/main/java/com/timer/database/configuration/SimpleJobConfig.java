package com.timer.database.configuration;


import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobRootConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.timer.database.bean.DSimpleJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class SimpleJobConfig {

    @Resource
    private CoordinatorRegistryCenter regCenter;

/*    @Resource
    private JobEventConfiguration jobEventConfiguration;*/

    @Resource
    private DSimpleJob simpleJob;


    /**
     *  http://elasticjob.io/docs/elastic-job-lite/02-guide/config-manual/
     *  jobName :  作业名称
     *  cron ：  cron表达式
     *  shardingTotalCount    作业分片总数
     *  shardingItemParameters	 分片序列号和参数用等号分隔，多个键值对用逗号分隔
     *                           分片序列号从0开始，不可大于或等于作业分片总数   如：0=a,1=b,2=c
     * jobParameter	 作业自定义参数
     *               作业自定义参数，可通过传递该参数为作业调度的业务方法传参，用于实现带参数的作业
     *               例：每次获取的数据量、作业实例从数据库读取的主键等
     * failover	 是否开启任务执行失效转移，开启表示如果作业在一次任务执行中途宕机，允许将该次未完成的任务在另一作业节点上补偿执行
     * misfire	是否开启错过任务重新执行
     * description	作业描述信息
     * jobProperties  配置jobProperties定义的枚举控制Elastic-Job的实现细节
     *                JOB_EXCEPTION_HANDLER用于扩展异常处理类
     *                EXECUTOR_SERVICE_HANDLER用于扩展作业处理线程池类
     * */
    @Bean
    public JobScheduler simpleJobScheduler() {

        //作业核心配置信息，作业名称、CRON表达式、分片总数。
        JobCoreConfiguration coreConfig = JobCoreConfiguration.
                newBuilder(StringAlias.simpleJobName, StringAlias.cron, StringAlias.sharddingCount)
                .build();
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(coreConfig, DSimpleJob.class.getCanonicalName());

        LiteJobConfiguration liteJobConfig = LiteJobConfiguration.newBuilder(simpleJobConfig)
             //   .jobShardingStrategyClass("../strategy/ShardingForServerStrategy")
                .overwrite(true)
                .build();
        SpringJobScheduler scheduler = new SpringJobScheduler(simpleJob, regCenter, liteJobConfig);
        scheduler.init();
        return scheduler;
    }

   /* // 定义作业核心配置
    @Bean
    public JobCoreConfiguration jobCoreConfiguration() {
        return JobCoreConfiguration.newBuilder("demoSimpleJob", "0/15 * * * * ?", 10).build();
    }

    // 定义SIMPLE类型配置
    @Bean
    public SimpleJobConfiguration simpleCoreConfig() {
       return new SimpleJobConfiguration(jobCoreConfiguration(), SimpleDemoJob.class.getCanonicalName());
    }
    // 定义Lite作业根配置
    @Bean
    public JobRootConfiguration rootConfiguration() {
        return   LiteJobConfiguration.newBuilder(simpleJobConfig).build();
    }*/
}
