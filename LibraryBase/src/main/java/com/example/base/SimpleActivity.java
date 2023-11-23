package com.example.base;


public abstract class SimpleActivity extends BaseActivity<ISimplePresenter> {
    @Override
    protected ISimplePresenter createPresenter() {
        return new SimplePresenter();
    }
}
