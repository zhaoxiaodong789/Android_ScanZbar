package com.example.xch.scanzbar;

import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    public static void login(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection;

                try {
                    connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setRequestMethod("GET");
                    connection.getInputStream();


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


}
