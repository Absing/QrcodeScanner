package com.storm;

import android.app.Application;

import com.storm.bean.MyObjectBox;

import io.objectbox.BoxStore;

/**
 * Created by Ding on 2017/9/9.
 */

public class App extends Application {
    private BoxStore mBoxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        mBoxStore = MyObjectBox.builder().androidContext(this).build();
    }

    public BoxStore getBoxStore() {
        return mBoxStore;
    }
}
