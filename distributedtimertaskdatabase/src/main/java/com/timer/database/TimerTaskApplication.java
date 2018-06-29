package com.timer.database;

import com.timer.database.bean.CommitData;
import com.timer.database.configuration.Alias;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class TimerTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimerTaskApplication.class,args);
    }

    @EventListener
    public void getDataEvent(ApplicationContextEvent event) {

        System.out.println("event begin");
        final Stat stat = new Stat();
        final ZooKeeper zooKeeper = CommitData.zooKeeperInit("99.6.145.116:2181");
        try {
            if(zooKeeper.exists(Alias.executeInfoNodeName,null) == null) {
                zooKeeper.create(Alias.executeInfoNodeName,"".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            }

        Watcher watcher = new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                try {
                    byte[] data = zooKeeper.getData(Alias.executeInfoNodeName,this,stat);
                    System.out.println("get new data from zk --------"+new String(data));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
            //zooKeeper.create("/zk_test","hello".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            byte[] data = zooKeeper.getData(Alias.executeInfoNodeName,watcher,stat);
            System.out.println(new String(data));
            zooKeeper.close();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
