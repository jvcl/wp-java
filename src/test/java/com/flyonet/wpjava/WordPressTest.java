package com.flyonet.wpjava;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created By jorge 14/08/15
 */
public class WordPressTest {
    WordPress wp;
    @Before
    public void setUp() throws Exception {
        wp = new WordPress("http://localhost:8888/", "admin", "admin");

    }

    @Test
    public void testFixURL() throws Exception {
        assertEquals("http://localhost:8888", wp.getUrl());
    }

    @Test
    public void testCreatePost() throws Exception {
        Random r = new Random();
        String title = "Test" + r.nextInt(1000);
        String content =  "Test";
        Post p = new Post(title, content, Post.TYPE.POST, Post.STATUS.PUBLISH);
        Post newPost = wp.createPost(p);
        assertEquals(title,newPost.getTitle());
        assertEquals("post",newPost.getType());
        assertEquals("publish",newPost.getStatus());
    }

    @Test
    public void testCreatePost2() throws Exception {
        Random r = new Random();
        String title = "Test" + r.nextInt(1000);
        String content =  "Test";
        Post p = new Post(title, content, Post.TYPE.PAGE, Post.STATUS.DRAFT);
        Post newPost = wp.createPost(p);
        assertEquals(title,newPost.getTitle());
        assertEquals("page",newPost.getType());
        assertEquals("draft",newPost.getStatus());
    }

    @Test
    public void testGetPost() throws Exception {
        Post existingPost = wp.getPost(1);
        assertEquals("Hello world!",existingPost.getTitle());
        assertEquals(1,existingPost.getID());
        assertEquals("post",existingPost.getType());
        assertEquals("publish",existingPost.getStatus());
    }
}
