package com.mc.universal_library.http;


import com.lidroid.xutils.util.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 马冲 on 2016/8/10.
 */

public class GetHttpUtils {
    public static String getData(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int responseCode = connection.getResponseCode();
            LogUtils.i(">>>>>>>>>>>>>>>>>>>>" + responseCode);
            if (responseCode == 200) {
                InputStream stream = connection.getInputStream();
                String json = InputStreamToStr(stream);
                return json;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String InputStreamToStr(InputStream stream) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while ((len = stream.read(arr)) != -1) {
                baos.write(arr, 0, len);
            }
            LogUtils.i(baos.toString());
            return baos.toString("utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
