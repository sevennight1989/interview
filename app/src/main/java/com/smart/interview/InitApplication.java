package com.smart.interview;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by lenovo on 17-12-13.
 */

public class InitApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
