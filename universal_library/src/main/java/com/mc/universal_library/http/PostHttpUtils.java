package com.mc.universal_library.http;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostHttpUtils {

    public static String getData(String path) {
        try {
            URL url = new URL(path);
            Log.i("xxx", path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            connection.setRequestProperty("Charsert", "UTF-8");
            Log.i("xxx", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + connection.getResponseCode());
            if (connection.getResponseCode() == 200) {
                InputStream inputStream = connection.getInputStream();
                String json = InputStreamToStr(inputStream);
                return json;
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String InputStreamToStr(InputStream inputStream) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while ((len = inputStream.read(arr)) != -1) {
                baos.write(arr, 0, len);
            }
            Log.i("xxx", baos.toString());
            return baos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
