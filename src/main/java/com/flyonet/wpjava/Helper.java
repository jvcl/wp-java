package com.flyonet.wpjava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jorge on 14/08/15.
 */
public class Helper {

    public static String getJSON(String url) throws IOException {

        String json = "";
        URL urlConn = new URL(url);
        HttpURLConnection yc = (HttpURLConnection) urlConn.openConnection();
        yc.connect();
        int response = yc.getResponseCode();
        try {
            if (response == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        yc.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    //System.out.println(inputLine);
                    json = json + inputLine;
                }
                in.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            yc.disconnect();
        }
        return json;
    }
}
