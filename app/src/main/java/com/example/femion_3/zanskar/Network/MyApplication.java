package com.example.femion_3.zanskar.Network;

/**
 * Created by Femion-3 on 06/07/2015.
 */

import android.app.Application;
import android.content.Context;

/**
 * Created by Windows on 30-01-2015.
 */
public class MyApplication extends Application {

    private static MyApplication sInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MyApplication getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

}
