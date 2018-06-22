package com.timer.database;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        for (Integer i:list
             ) {
            System.out.println(i);
            list.add(2);
        }
    }
}
