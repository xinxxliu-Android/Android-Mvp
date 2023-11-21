package com.lt.utils;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxSchedulers {
    public static Scheduler request(){
        return Schedulers.io();
    }
    public static Scheduler single(){
        return Schedulers.single();
    }
    public static Scheduler computation(){
        return Schedulers.computation();
    }
    public static Scheduler main(){
        return AndroidSchedulers.mainThread();
    }
}
