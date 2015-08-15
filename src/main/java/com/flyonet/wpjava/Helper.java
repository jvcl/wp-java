package com.flyonet.wpjava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;


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

public class Helper {

    public static String getJSON(String url) throws IOException {
        String json = null;
        URL urlConn = new URL(url);
        HttpURLConnection yc = (HttpURLConnection) urlConn.openConnection();
        yc.connect();
        int response = yc.getResponseCode();
        try {
            if (response == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        yc.getInputStream()));
                String inputLine;
                json = "";
                while ((inputLine = in.readLine()) != null) {
                    json = json + inputLine;
                }
                in.close();
            }else {
                System.out.println("Error: Server response: "+ response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            yc.disconnect();
        }
        return json;
    }

    public static void postJSON(String url, Page page, String username, String password) {
        Gson gson = new Gson();
        String json = gson.toJson(page);
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
            } else if (responseCode != HttpURLConnection.HTTP_CREATED){
                System.out.println("Response "+ responseCode + " Error creating post in Wordpress. Check user/password and url");
            }

            yc.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
