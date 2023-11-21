package com.lt.func;

import android.app.Application;

public final class PluginInterface {
    static Application appContext;
    public static void install(Application app){
        appContext = app;
    }
}
