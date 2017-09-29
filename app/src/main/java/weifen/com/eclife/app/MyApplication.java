package weifen.com.eclife.app;

import android.app.Application;

import weifen.com.common.app.BaseApplication;

/**
 * Created by zhurencong on 2017/9/17.
 */
public class MyApplication extends BaseApplication{

    //保存当前位置
    public static String location="";

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
