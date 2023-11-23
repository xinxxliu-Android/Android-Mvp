package com.example.base;

import android.content.Context;

import androidx.annotation.StringRes;

public interface IAbstractBaseView {
    void showMessage(String message);
    void showMessage(@StringRes int messageRes);
    void showLongMessage(String message);
    void showLongMessage(@StringRes int messageRes);

    Context getContext();
    void post(Runnable runnable);
    void postDelay(Runnable runnable, long timeMillis);


}
