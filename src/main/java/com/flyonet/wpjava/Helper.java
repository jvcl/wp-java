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
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import sun.net.www.http.HttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The Helper handles all connection to the Wordpress API using {@code HttpURLConnection}
 */
public class Helper {

    public static String getJSON(String url) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse response) throws IOException {
                if (response.getStatusLine().getStatusCode() == 200){
                    HttpEntity entity = response.getEntity();
                    if (entity != null){
                        return EntityUtils.toString(entity);
                    }
                }
                return null;
            }
        };

        return httpClient.execute(httpGet, responseHandler);
    }

    public static String postJSON(String url, String username, String password, String json) throws IOException {

        StringEntity entity = new StringEntity(json);
        entity.setContentType("application/json");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        String authString = username + ":" + password;
        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
        String authStringEnc = new String(authEncBytes);

        post.setHeader("Authorization", "Basic " + authStringEnc);
        post.setEntity(entity);

        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse response) throws IOException {
                if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201){
                    HttpEntity entity = response.getEntity();
                    if (entity != null){
                        return EntityUtils.toString(entity);
                    }
                }
                return null;
            }
        };

        return httpClient.execute(post, responseHandler);
    }

    public static void uploadFile(String urlInput, String username, String password) {

    }
}
