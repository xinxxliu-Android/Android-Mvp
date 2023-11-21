package com.example.base;

import android.content.Context;
/**
 * 全局 module抽象基类。
 * 界面不允许直接继承该类进行实现。 如需实现界面，继承{@link BaseModel}进行实现
 */

class AbstractBaseModel implements IAbstractBaseModel {
    protected final String TAG;
    {
        String simpleName = getClass().getSimpleName();
        TAG = simpleName.length() > 20 ? simpleName.substring(simpleName.length() - 20):simpleName;
    }
    protected Context mContext;
    @Override
    public void attach(Context c) {
        mContext = c;
    }

    @Override
    public void detach() {
        mContext = null;
    }
}
