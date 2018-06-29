package com.timer.database.bean;

import com.alibaba.fastjson.JSON;
import com.timer.database.configuration.Alias;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommitData implements Runnable {

    private String begin;
    private String end;
    CommitData(String begin,String end) {
        this.begin = begin;
        this.end = end;
    }
    @Override
    public void run() {
        ZooKeeper zooKeeper = zooKeeperInit("99.6.145.116:2181");
        List<Map<String,String>> taskInfo;
        Stat stat = null;
        try {
            stat = zooKeeper.exists(Alias.executeInfoNodeName,null);
            byte[] oldData = zooKeeper.getData(Alias.executeInfoNodeName,false,stat);
            taskInfo = (oldData.length==0?new ArrayList<Map<String, String>>():JSON.parseObject(new String(oldData),List.class));
            if(taskInfo.size()>10) {
                taskInfo.remove(0);
            }
            Map<String,String> newInfo = new HashMap<String, String>();
            newInfo.put("beginTime",begin);
            newInfo.put("success","true");
            newInfo.put("endTime",end);
            newInfo.put("server","99.6.145.116:8080");
            taskInfo.add(newInfo);
            String newData = JSON.toJSONString(taskInfo);
            zooKeeper.setData(Alias.executeInfoNodeName,newData.getBytes(),stat.getVersion());
            zooKeeper.close();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static ZooKeeper zooKeeperInit(String serverList) {
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("zk connect init");
            }
        };
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(serverList,3000,watcher);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  zk;
    }
}
