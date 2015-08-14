package com.flyonet.wpjava;

import java.io.IOException;

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

    public WordPress(String url, String username, String password) {
        this.url = url;
        if (this.url.charAt(this.url.length()-1) == '/')
            fixURL();
        this.username = username;
        this.password = password;
    }

    private void fixURL() {
        url = url.substring(0,url.length()-1);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPost(int ID){
        String json = null;
        try {
            json = Helper.getJSON(url + "/wp-json/posts/"+ID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
