package com.nexless;

import android.app.Application;

import com.nexless.locstarcard.CardManager;


/**
 * @date: 2018/12/19
 * @author: su qinglin
 * @description:
 */
public class AppManager extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CardManager.getInstance().init();
        CardManager.getInstance().setDebug(true);
    }
}
