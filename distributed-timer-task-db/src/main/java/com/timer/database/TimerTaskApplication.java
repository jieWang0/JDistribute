package com.timer.database;

import com.timer.database.bean.DSimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TimerTaskApplication {


    public static void main(String[] args) {
        SpringApplication.run(TimerTaskApplication.class,args);
    }
}
