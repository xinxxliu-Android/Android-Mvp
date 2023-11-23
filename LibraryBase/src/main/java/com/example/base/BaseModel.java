package com.example.base;

import com.lt.http.LibraryHttp;

public class BaseModel extends AbstractBaseModel
implements IBaseModel{
    private Object t;

    /**
     * 构建网络请求的 serviceApi接口，确保单例化事例话
     * @param tClass    接口类
     * @param <T>       接口类型
     * @return          接口对象
     */
    @SuppressWarnings("unchecked")
    protected synchronized <T> T serviceApi(Class<T> tClass){
        if (t == null)
            t = LibraryHttp.retrofitRequest().build(tClass);
        return (T) t;
    }

    @Override
    public void detach() {
        t = null;
        super.detach();
    }
}
