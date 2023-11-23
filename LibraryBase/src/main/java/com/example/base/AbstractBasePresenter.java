package com.example.base;

 abstract class AbstractBasePresenter<T extends IAbstractBaseView, S extends IAbstractBaseModel> implements IAbstractBasePresenter<T> {
    protected T mView;
    protected S mModel;
    protected final String TAG;
     {
         String simpleName = getClass().getSimpleName();
         TAG = simpleName.length() > 20 ? simpleName.substring(simpleName.length() - 20):simpleName;
     }
    @Override
    public void attach(T t) {
        mView = t;
        attachModel();
    }

    protected void attachModel() {
        mModel = createModel();
        if (mModel != null)
            mModel.attach(mView.getContext());
    }

    protected abstract S createModel();

    @Override
    public void detach() {
        mView = null;
        if (mModel != null)
            mModel.detach();
        mModel = null;
        Runtime.getRuntime().gc();
    }
}
