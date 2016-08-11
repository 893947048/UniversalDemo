package com.bw.universaldemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.universaldemo.Model.Bean.Data;
import com.bw.universaldemo.adapter.MyBaseAdapter;
import com.google.gson.Gson;
import com.mc.universal_library.BaseLoginActivity;
import com.mc.universal_library.encryption.MD5Util;
import com.mc.universal_library.http.GetHttpUtils;
import com.mc.universal_library.system.AppUtils;
import com.mc.universal_library.system.GPSUtils;
import com.mc.universal_library.system.SDCardUtils;
import com.mc.universal_library.system.ScreenUtils;
import com.mc.universal_library.toolsutils.DeviceStatusUtils;
import com.mc.universal_library.toolsutils.NetUtils;
import com.mc.universal_library.toolsutils.ToastUtils;
import com.mc.universal_library.toolsutils.TransitionTime;
import com.mc.universal_library.xlistview.XListView;

import java.util.List;

public class MainActivity extends Activity implements XListView.IXListViewListener {

    //测试上传
    public String path = "http://op.juhe.cn/onebox/movie/video?dtype=1&q=%E8%91%AB%E8%8A%A6%E5%A8%83&key=321b198313eefa7cca326d0fdb7dbe10";
    MyBaseAdapter adapter;
    XListView xlv;
    String data;
    ImageView imageView;
    List<Data.ResultBean.ActSBean> act_s;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Gson gson = new Gson();
                Data json = gson.fromJson(data, Data.class);
                Data.ResultBean dataResult = json.getResult();
                act_s = dataResult.getAct_s();
                //...
                adapter = new MyBaseAdapter(MainActivity.this, act_s);
                xlv.setAdapter(adapter);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xlv = (XListView) findViewById(R.id.xlv);

        xlv.setPullLoadEnable(true);
        xlv.setPullRefreshEnable(true);
        xlv.setXListViewListener(this);
        imageView = (ImageView) findViewById(R.id.imageView);
        Glide.with(MainActivity.this).load(R.mipmap.ic_launcher).asGif().into(imageView);
        /**
         * 判断网络连接
         * */
        //判断网络是否连接
        boolean connected = NetUtils.isConnected(this);
        if (connected == true) {
            Log.i("xxx", "网络正常");
        } else {
            Log.i("xxx", "网络不正常");
        }


        /**
         * 判断是否wifi
         * */
        boolean wifi = NetUtils.isWifi(this);
        if (wifi == true) {
            Log.i("xxx", "有wifi");
        } else {
            Log.i("xxx", "没有wifi");
        }

        /**
         * 获取当前系统时间utm转换成带描述的日期
         * */
        long l = System.currentTimeMillis();
        String displayTimeAndDesc = TransitionTime.getDisplayTimeAndDesc(l);
        Log.i("xxx", "带描述的时间" + displayTimeAndDesc);

        /**
         * 打开网络设置页面
         * */
        NetUtils.setNetworkMethod(this);

        /**
         * 获取应用的名字和版本号
         * */
        String appName = AppUtils.getAppName(this);
        String versionName = AppUtils.getVersionName(this);
        Log.i("xxx", appName + "------------" + versionName);
        /**
         * 土司
         * */
        ToastUtils.showToast(MainActivity.this, appName);

        /**
         * 判断sd卡是存在
         * */
        boolean sdCardEnable = SDCardUtils.isSDCardEnable();
        if (sdCardEnable == true) {
            ToastUtils.showToast(MainActivity.this, "sd卡可用");
            Log.i("xxx", "sd卡可用");
        } else {
            ToastUtils.showToast(MainActivity.this, "sdk卡不可用");
            Log.i("xxx", "sdk卡不可用");
        }


        /**
         * 获取sd卡路径
         * */
        String directoryPath = SDCardUtils.getRootDirectoryPath();
        Log.i("xxxx", "sd卡的路径是:" + directoryPath);

        /**
         *获取sd卡剩余空间
         * */
        long sdCardAllSize = SDCardUtils.getSDCardAllSize();
        Log.i("xxxx", "sd卡剩余空间" + sdCardAllSize);
        /**
         *判断sd卡的剩余空间
         *  directoryPath 为sd卡的路径
         * */
        final long freeBytes = SDCardUtils.getFreeBytes(directoryPath);
        Log.i("xxx", "当前sd卡所剩空间为:" + freeBytes);

        /**
         * 打开/关闭 GPS
         * */
        GPSUtils.toggleGPS(this);
        /**
         * 获取网络数据的工具类
         * */
        //get请求

        new Thread(new Runnable() {
            @Override
            public void run() {
                //post请求
                //String data1 = PostHttpUtils.getData(path);

                data = GetHttpUtils.getData(path);
                Log.i("xxxx", data);
                handler.sendEmptyMessage(1);
            }
        }).start();
        /**
         * MD5加密
         * */
        String pwd = "123456";
        String md5String = MD5Util.getMD5String(pwd);
        Log.i("xxxx", "MD5加密后的数据是:" + md5String);

        /**
         * 获取屏幕高度宽度
         * */
        //高度
        int screenHeight = ScreenUtils.getScreenHeight(this);
        //宽度
        int screenWidth = ScreenUtils.getScreenWidth(this);


        /**
         * 获取屏幕的亮度和设置屏幕的亮度
         * */
        //获取手机屏幕亮度
        int screenBrightness = ScreenUtils.getScreenBrightness(this);
        Log.i("xxx", "当前手机屏幕亮度为:" + screenBrightness);


    }

    public void jump(View v) {

        /**
         * 登陆页面啊
         * */
        Intent intent = new Intent(MainActivity.this, BaseLoginActivity.class);
        startActivity(intent);
    }

    /**
     * 设置媒体音量
     *
     * @paramthis 上下文
     * @paramringVloume 音量
     */
    public void soudup(View v) {
        //无显示
//        DeviceStatusUtils.setRingVolume(MainActivity.this, 10);

        //有音量显示
        DeviceStatusUtils.setRingVolumeUI(MainActivity.this, true);
    }


    /**
     * 设置媒体音量
     *
     * @paramthis 上下文
     * @paramringVloume 音量
     */

    public void souddown(View v) {
        //无显示
//        DeviceStatusUtils.setRingVolume(MainActivity.this, -10);

        //有音量显示
        DeviceStatusUtils.setRingVolumeUI(MainActivity.this, false);
    }


    @Override
    public void onRefresh() {
        act_s.add(new Data.ResultBean.ActSBean("xxx", "xxxx"));
        adapter.notifyDataSetChanged();
        xlv.stopRefresh();
    }

    @Override
    public void onLoadMore() {
        act_s.add(new Data.ResultBean.ActSBean("xxx", "xxxx"));
        adapter.notifyDataSetChanged();
        xlv.stopLoadMore();
    }
}
