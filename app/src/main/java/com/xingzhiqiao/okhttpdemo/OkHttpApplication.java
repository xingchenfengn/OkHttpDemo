package com.xingzhiqiao.okhttpdemo;

import android.app.Application;

/**
 * Created by xingzhiqiao on 2017/4/27.
 */

public class OkHttpApplication extends Application {


    private static OkHttpApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static OkHttpApplication getInstance() {
        return mInstance;
    }


}
