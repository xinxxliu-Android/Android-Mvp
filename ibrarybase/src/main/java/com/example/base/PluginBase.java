package com.example.base;

import android.app.Application;

public final class PluginBase {
    static Application mAppContext;
    public static void install(Application app){
        mAppContext = app;
    }
}
