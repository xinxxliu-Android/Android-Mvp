package com.example.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 抽象 Fragment界面
 * 界面不允许直接继承该类进行实现。 如需实现界面，继承{@link BaseFragment}进行实现
 *
 * @param <T> 当前v层所关联的P层的类型，类型为接口类型，不建议使用实现类类型
 */
abstract class AbstractBaseFragment<T extends IAbstractBasePresenter> extends Fragment
        implements IAbstractBaseView {
    /**
     * 抽象tag 方便log打印日志。
     * 采用：当前类名，并保持最长20长度
     */
    protected final String TAG;

    {
        String simpleName = getClass().getSimpleName();
        TAG = simpleName.length() > 20 ? simpleName.substring(simpleName.length() - 20) : simpleName;
    }

    /**
     * 当前Fragment的容器Activity对象。
     * 不可为空，即 fragment不可能无容器
     * 持有于 {@link #onAttach(Context)}
     * 释放于 {@link #onDetach()}
     */
    protected Activity mContext;
    /**
     * 当前v层所关联的p层对象。
     * 构建方式：{@link #createPresenter()}
     * 在{@link #onViewCreated(View, Bundle)}中构建
     * 在{@link #onDestroyView()}中销毁
     * 该对象不可为空，否则崩溃报错。
     */
    protected T mPresenter;
    /**
     * 当前Fragment 的 布局对象
     */
    protected View containerView;
    /**
     * butterKnife功能使用。
     * 在{@link #onViewCreated(View, Bundle)}中构建
     * 在{@link #onDestroyView()}中销毁
     * 该对象不可为空。否则崩溃。
     * 即：当前界面不可能无布局
     * 具体使用参考：
     * https://github.com/JakeWharton/butterknife
     */
    private Unbinder unbinder;

    /**
     * fragment 数据传输
     */
    protected Bundle data;

    /**
     * 替换 {@link #setArguments(Bundle)}函数
     * @param data
     */
    public void setData(Bundle data) {
        this.data = data;
    }

    public Bundle getData() {
        return data;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (view == null)
            view = inflater.inflate(layoutId(), container, false);
        containerView = view;
        unbinder = ButterKnife.bind(this, containerView);
        attachPresenter();
        return view;
    }

    @SuppressWarnings("unchecked")
    private void attachPresenter() {
        mPresenter = createPresenter();
        if (mPresenter != null)
            mPresenter.attach(this);
    }

    @Override
    public void showMessage(String message) {
        realShowMsg(message, false);
    }

    private void realShowMsg(String msg, boolean longShow) {
        if (longShow)
            ToastUtils.showLong(msg);
        else
            ToastUtils.showShort(msg);
    }

    @Override
    public void showMessage(@StringRes int messageRes) {
        showMessage(getString(messageRes));
    }

    @Override
    public void showLongMessage(String message) {
        realShowMsg(message, true);
    }

    @Override
    public void showLongMessage(@StringRes int messageRes) {
        showLongMessage(getString(messageRes));
    }

    /**
     * 同 {@link Handler#post(Runnable)}
     *
     * @param runnable 同{@link Handler#post(Runnable)}
     */
    public final void post(Runnable runnable) {
        ThreadUtils.getMainHandler().post(runnable);
    }

    /**
     * 同 {@link Handler#postDelayed(Runnable, long)}
     *
     * @param runnable   同 {@link Handler#postDelayed(Runnable, long)}
     * @param timeMillis 同 {@link Handler#postDelayed(Runnable, long)}
     */
    public final void postDelay(Runnable runnable, long timeMillis) {
        ThreadUtils.getMainHandler().postDelayed(runnable,timeMillis);
    }

    protected abstract T createPresenter();

    @Override
    public void onDestroyView() {
        if (mPresenter != null)
            mPresenter.detach();
        mPresenter = null;
        unbinder.unbind();
        unbinder = null;
        containerView = null;
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        mContext = null;
        super.onDetach();
    }

    /**
     * 当前页面，布局文件
     *
     * @return int
     */
    protected abstract int layoutId();

    @Nullable
    @Override
    public final Context getContext() {
        return mContext;
    }
}
