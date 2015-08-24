/*
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

package com.flyonet.wpjava;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Wordpress class that implements the most used functions of the API
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
     * Get a Post from the wordpress resource
     * @param ID the ID of the page to be retrieved
     * @return a Post instance
     */
    public Post getPost(int ID){
        String json = null;
        Post post = null;
        try {
            json = Helper.getJSON(url + "/wp-json/posts/"+ID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (json != null){
            Gson gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .create();
            post = gson.fromJson(json, Post.class);
        }
        post.fixContent();
        return post;
    }

    /**
     * Edit an existing post. The post must have the {@code ID}, {@code title} and {@code content_raw}
     * set
     * @param post the post to be edited and send to Wordpress to be updated
     */
    public void editPost(Post post){
        if (post.getID() != 0){
            Gson gson =  new Gson();
            try {
                Helper.postJSON(url + "/wp-json/posts/"+post.getID(), username, password, gson.toJson(post));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns all posts of the Wordpress blog.
     * @return a Collection of Posts of type "post"
     */
    public Collection<Post> getAllPosts(){
        return getPostOrPages("/wp-json/posts/");
    }

    /**
     * Returns all Pages from the Wordpress blog
     * @return a Collection of Pages
     */
    public Collection<Post> getAllPages(){
        return getPostOrPages("/wp-json/posts?type=page");
    }

    /**
     * Get the posts or pages from a Wordpress blog
     * @param query query and filter to the Wordpress API
     * @return a collections of pages
     */
    private Collection<Post> getPostOrPages(String query){
        String json = null;
        Collection<Post> posts = null;
        try {
            json = Helper.getJSON(url + query);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (json != null){
            Gson gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .create();
            Type collectionType = new TypeToken<Collection<Post>>(){}.getType();
            posts = gson.fromJson(json, collectionType);
        }
        for (Post p : posts) {
            p.fixContent();
        }
        return posts;
    }

    /**
     * Create a post in Wordpress
     * @param post the post to be created in Wordpress.
     */
    public Post createPost(Post post)  {
        String json = null;
        Gson g = new Gson();
        try {
            json = Helper.postJSON(url + "/wp-json/posts/", username, password, g.toJson(post));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (json != null){
            Gson gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .create();
           return gson.fromJson(json, Post.class);
        }
        return null;
    }

    /**
     * Upload a media item to the WordPress resource
     * @param mediaItem The item to be uploaded
     */
    public void uploadMedia(MediaItem mediaItem){
        Helper.uploadFile(url + "/wp-json/media/", username, password);
    }
}
