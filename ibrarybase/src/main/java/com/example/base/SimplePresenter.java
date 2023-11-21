package com.example.base;

public class SimplePresenter extends BasePresenter<ISimpleView,ISimpleModel>
implements ISimplePresenter{
    @Override
    protected SimpleModel createModel() {
        return new SimpleModel();
    }
}
