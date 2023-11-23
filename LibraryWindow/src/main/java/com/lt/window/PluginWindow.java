package com.lt.window;

import android.app.Application;

import com.lt.http.LibraryHttp;

public final class PluginWindow {
    static Application mAppContext;
    public static void install(Application app){
        mAppContext = app;
        LibraryHttp.addInterceptor(new TokenInterceptor());
    }
}
