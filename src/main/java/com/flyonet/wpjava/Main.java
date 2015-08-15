package com.flyonet.wpjava;

import java.util.Collection;

/**
 * Created by jorge on 28/07/15.
 */
public class Main {

    public static void main(String[] args)  {
        WordPress wp = new WordPress("http://localhost:8888/","jorge","1843");

        Collection<Page> collection = wp.getAllPosts();
        for (Page p : collection){
            System.out.println(p);
        }

        collection = wp.getAllPages();
        for (Page p : collection){
            System.out.println(p);
        }

    }
}
