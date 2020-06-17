package com.test.sate;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Context sAppContext;
    @Override
    public void onCreate() {
        super.onCreate();
        DBUtils.getInstance().creads(getApplicationContext());
        sAppContext = getApplicationContext();
    }
    public static Context getAppContext() {
        return sAppContext;
    }
}
