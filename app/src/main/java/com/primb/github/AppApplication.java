package com.primb.github;

import android.app.Application;

/**
 * @author Chen
 * @date 2018/3/7
 * 功能描述：
 */

public class AppApplication extends Application {
    private static AppApplication application;
    private final String TAG = AppApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static AppApplication get(){
        return application;
    }
}
