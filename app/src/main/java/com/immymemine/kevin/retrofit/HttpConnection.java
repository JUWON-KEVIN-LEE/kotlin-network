package com.immymemine.kevin.retrofit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by quf93 on 2017-12-19.
 */

public class HttpConnection {

    public HttpURLConnection getHttpConnection(String urlString) {

        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(urlString);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return urlConnection;
    }

    public String getResult() {

        String result = "";

        try {

            InputStream inputStream = getHttpConnection("http://localhost:9999").getInputStream();
            StringBuffer buffer = new StringBuffer();

            if( inputStream == null ) {
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if(buffer.length() == 0) {
                return null;
            }
            result = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
