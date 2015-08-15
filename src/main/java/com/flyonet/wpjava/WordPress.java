package com.flyonet.wpjava;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * This file is part of WP-JAVA.
 *
 *  WP-JAVA is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  WP-JAVA is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with WP-JAVA.  If not, see <http://www.gnu.org/licenses/>.
 */

public class WordPress {
    private String url;
    private String username;
    private String password;

    /**
     * Construct a Wordpress resource instance
     * @param url url of the wordpress resource
     * @param username username to connect
     * @param password password of the user
     */
    public WordPress(String url, String username, String password) {
        this.url = url;
        if (this.url.charAt(this.url.length()-1) == '/')
            fixURL();
        this.username = username;
        this.password = password;
    }

    /**
     * Remove the slash in the url
     */
    private void fixURL() {
        url = url.substring(0,url.length()-1);
    }

    /**
     * Get the url
     * @return url of wordpress reource
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the url of the wordpress resource.
     * @param url url of the Wordpress rosource ie. http://wp-api.org
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get a Page from the wordpress resource
     * @param ID the ID of the page to be retrieved
     * @return a Page instance
     */
    public Page getPage(int ID){
        String json = null;
        Page page = null;
        try {
            json = Helper.getJSON(url + "/wp-json/posts/"+ID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (json != null){
            Gson gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .create();
            page = gson.fromJson(json, Page.class);
        }
        return page;
    }

    /**
     * Returns all posts of the Wordpress blog.
     * @return a Collection of Pages of type "post"
     */
    public Collection<Page> getAllPosts(){
        String json = null;
        Collection<Page> pages = null;
        try {
            json = Helper.getJSON(url + "/wp-json/posts/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (json != null){
            Gson gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .create();
            Type collectionType = new TypeToken<Collection<Page>>(){}.getType();
            pages = gson.fromJson(json, collectionType);
        }
        return pages;
    }

    /**
     * Returns all Pages from the Wordpress blog
     * @return a Collection of Pages
     */
    public Collection<Page> getAllPages(){
        String json = null;
        Collection<Page> pages = null;
        try {
            json = Helper.getJSON(url + "/wp-json/posts?type=page");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (json != null){
            Gson gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .create();
            Type collectionType = new TypeToken<Collection<Page>>(){}.getType();
            pages = gson.fromJson(json, collectionType);
        }
        return pages;
    }
}
