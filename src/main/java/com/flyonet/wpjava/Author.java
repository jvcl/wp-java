package com.flyonet.wpjava;

/**
 * Created By jorge 14/08/15
 */
public class Author {
    private int ID;
    private String username;
    private String name;
    private String first_name;
    private String last_name;

    @Override
    public String toString() {
        return ID + " " + username;
    }
}
