package com.flyonet.wpjava;

/**
 * Created by jorge on 28/07/15.
 */
public class Main {

    public static void main(String[] args)  {

        WordPress wp = new WordPress("http://localhost:8888/","jorge","1843");
        System.out.println(wp.getPost(1));
    }
}
