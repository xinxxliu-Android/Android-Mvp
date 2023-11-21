package com.example.base;

public abstract class BasePresenter<T extends IBaseView, S extends IBaseModel> extends AbstractBasePresenter<T, S>
        implements IBasePresenter<T> {

}
