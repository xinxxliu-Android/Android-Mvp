package com.example.base;

public class SimpleFragment extends BaseFragment<ISimplePresenter>
implements ISimpleView{
    @Override
    protected ISimplePresenter createPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return 0;
    }

    @Override
    public void startLoading() {

    }


    @Override
    public void endLoading() {

    }
}
