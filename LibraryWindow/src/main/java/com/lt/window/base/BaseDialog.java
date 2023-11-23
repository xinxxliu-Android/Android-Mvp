package com.lt.window.base;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.annotation.NonNull;

import com.lt.func.IBaseHelper;

public abstract class BaseDialog<T> extends Dialog implements IBaseHelper<T> {
    protected T t;

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void dismiss() {
        if (isShowing())
            super.dismiss();
    }

    @Override
    public boolean isShowing() {
        Window window = getWindow();
        if (window == null)
            return false;
        if (!window.isActive())
            return false;
        return super.isShowing();
    }

    @Override
    public void attach(T t) {
        this.t = t;
    }

    @Override
    public void detach() {
        cancel();
        this.t = null;
    }
}
