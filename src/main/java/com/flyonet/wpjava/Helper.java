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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
            public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
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

    public static String postJSON(String url, Post post, String username, String password) {
        Gson gson = new Gson();
        String json = gson.toJson(post);
        String responseJson = null;
        try {
            URL urlConn = new URL(url);
            HttpURLConnection yc = (HttpURLConnection) urlConn.openConnection();
            yc.setDoOutput(true);
            yc.setRequestProperty("Content-Type", "application/json");

            String authString = username + ":" + password;
            byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
            String authStringEnc = new String(authEncBytes);
            yc.setRequestProperty("Authorization", "Basic " + authStringEnc);

            OutputStream os = yc.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            int responseCode = yc.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_FORBIDDEN){
                System.out.println("Response "+ responseCode + "Check user/password");
            } else if (responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        yc.getInputStream()));
                String inputLine;
                responseJson = "";
                while ((inputLine = in.readLine()) != null) {
                    responseJson = responseJson + inputLine;
                }
                in.close();
            }else{
                System.out.println("Response " + responseCode + " Error creating post in WordPress. Check user/password and url");
            }

            yc.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseJson;
    }

    public static void uploadFile(String urlInput, String username, String password) {

    }
}
