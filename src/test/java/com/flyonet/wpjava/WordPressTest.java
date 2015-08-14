package com.flyonet.wpjava;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created By jorge 14/08/15
 */
public class WordPressTest {

    @Test
    public void testFixURL() throws Exception {
        WordPress wp = new WordPress("http://wp-api.org/", "test", "test");
        assertEquals("http://wp-api.org", wp.getUrl());
    }
}
