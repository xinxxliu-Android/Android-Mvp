package com.example.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 全局 activity抽象基类。
 * 界面不允许直接继承该类进行实现。 如需实现界面，继承{@link BaseActivity}进行实现
 *
 * @param <T> 当前v层所关联的P层的类型，类型为接口类型，不建议使用实现类类型
 */
abstract class AbstractBaseActivity<T extends IAbstractBasePresenter> extends AppCompatActivity
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
     * 当前v层所关联的p层对象。
     * 构建方式：{@link #createPresenter()}
     * 在{@link #onCreate(Bundle)}中构建
     * 在{@link #onDestroy()}中销毁
     * 该对象不可为空，否则崩溃报错。
     */
    protected T mPresenter;
    /**
     * 主线程handler对象。用以切换到主线程操作。
     * 封装到函数中{@link IAbstractBaseView#post(Runnable)} 及 {@link IAbstractBaseView#postDelay(Runnable, long)}的实现中使用
     * 方便在P层调用{@link BasePresenter#mView#post(Runnable)} 及 {@link BasePresenter#mView#postDelay(Runnable, long)}调用
     * 构建方式：{@link #createMainHandler()}
     * 可自定义继承{@link BaseHandler}实现当前界面专属的handler进行扩展
     * 默认实现为{@link SimpleHandler}
     * 在{@link #onCreate(Bundle)}中构建
     * 在{@link #onDestroy()}中销毁
     * 该对象不可为空，否则崩溃报错。
     */
    protected Handler mainHandler;
    /**
     * butterKnife功能使用。
     * 在{@link #onCreate(Bundle)}中构建
     * 在{@link #onDestroy()}中销毁
     * 该对象不可为空。否则崩溃。
     * 即：当前界面不可能无布局
     * 具体使用参考：
     * https://github.com/JakeWharton/butterknife
     */
    private Unbinder unbinder;

    /**
     * 引入布局时， 可使用布局文件 或 直接构建view 用以展示界面
     * {@link #layoutId()} 和 {@link #layoutView()} 只可选择一个进行实现
     * 且 仅当只实现{@link #layoutView()}时，函数{@link #layoutParams()}才可被调用，
     *
     * @param savedInstanceState 当前页面被异常销毁，恢复时包含的view数据
     *                           即：如果该对象不为空，说明当前界面是异常销毁重建
     *                           否则 就是全新界面
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        hideBottomUI();
        super.onCreate(savedInstanceState);
        //配置 window flag值，如 沉浸式、常亮、点亮屏幕等
        configWindowFlags(getWindow());
        //水滴屏适配、自定义挖孔屏适配
        configStatusBarBounds();
        int layoutResID = layoutId();
        if (layoutResID > 0)
            setContentView(layoutResID);
        else {
            ViewGroup.LayoutParams params = layoutParams();
            if (params != null)
                setContentView(layoutView(), params);
            else if (layoutView() != null) {
                setContentView(layoutView());
            } else {
                return;
            }
        }
        unbinder = ButterKnife.bind(this);
        //绑定p对象
        attachPresenter();
        //启动主线程Handler
        createMainHandler();
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDestroy() {
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(null);
        mainHandler.removeCallbacksAndMessages(null);
        mainHandler = null;
        unbinder.unbind();
        unbinder = null;
        if (mPresenter != null) {
            mPresenter.detach();
            mPresenter = null;
        }
        super.onDestroy();
    }

    /**
     * 实例化 主线程handler对象，用以实现线程/进程通讯
     * <p>
     * 该handler也可作为 当前模块间，mvp通讯桥梁
     * 默认实现为{@link SimpleHandler}的子类。如需定制实现，自行继承{@link BaseHandler}实现自己的Handler即可
     */
    protected void createMainHandler() {
        mainHandler = new SimpleHandler<>();
    }

    /**
     * 同 {@link Handler#post(Runnable)}
     *
     * @param runnable 同{@link Handler#post(Runnable)}
     */
    public void post(Runnable runnable) {
        if (mainHandler != null)
            mainHandler.post(runnable);
    }

    /**
     * 同 {@link Handler#postDelayed(Runnable, long)}
     *
     * @param runnable   同 {@link Handler#postDelayed(Runnable, long)}
     * @param timeMillis 同 {@link Handler#postDelayed(Runnable, long)}
     */
    public void postDelay(Runnable runnable, long timeMillis) {
        if (mainHandler != null)
            mainHandler.postDelayed(runnable, timeMillis);
    }

    /**
     * 如果当前使用 代码构建布局时，可使用该函数 与 {@link #layoutView()}函数 结合 用以动态声明当前页面布局。 相对布局文件方式，效率极高
     * 用以配置 当前界面展示布局详细配置信息
     * <p>
     * 该对象 可为空对象
     *
     * @return 布局控制参数
     */
    protected abstract @Nullable
    ViewGroup.LayoutParams layoutParams();

    /**
     * 如果当前使用 代码构建布局时，可使用该函数 与 {@link #layoutParams()}函数 结合 用以动态声明当前页面布局。 相对布局文件方式，效率极高
     * 用以配置 当前界面展示布局对象
     *
     * @return 当前页面布局
     */
    protected abstract View layoutView();

    /**
     * 绑定p对象
     */
    @SuppressWarnings("unchecked")
    private void attachPresenter() {
        mPresenter = createPresenter();
        if (mPresenter != null)
            mPresenter.attach(this);
    }

    /**
     * 构建P 对象
     *
     * @return 当前构建的P对象
     */
    protected abstract T createPresenter();

    /**
     * 如果有需要，配置当前页面的 状态栏参数
     * 如：水滴屏，挖孔屏等空间适配。避免缺口处有数据/多媒体展示，影响展示效果
     * <p>
     * DisplayCutout displayCutout = windowInsets.getDisplayCutout();
     * <p>
     * Log.e( "TAG", "安全区域距离屏幕左边的距离 SafeInsetLeft:"+ displayCutout.getSafeInsetLeft());
     * <p>
     * Log.e( "TAG", "安全区域距离屏幕右部的距离 SafeInsetRight:"+ displayCutout.getSafeInsetRight());
     * <p>
     * Log.e( "TAG", "安全区域距离屏幕顶部的距离 SafeInsetTop:"+ displayCutout.getSafeInsetTop());
     * <p>
     * Log.e( "TAG", "安全区域距离屏幕底部的距离 SafeInsetBottom:"+ displayCutout.getSafeInsetBottom());
     * <p>
     * // 获得刘海区域
     * List<Rect> rects = displayCutout.getBoundingRects();
     * <p>
     * if(rects == null|| rects.size() == 0) {
     * <p>
     * Log.e( "TAG", "不是刘海屏");
     * <p>
     * } else{
     * <p>
     * Log.e( "TAG", "刘海屏数量:"+ rects.size());
     * <p>
     * for(Rect rect : rects) {
     * <p>
     * Log.e( "TAG", "刘海屏区域："+ rect);
     * <p>
     * }
     */
    protected void configStatusBarBounds() {
        BarUtils.setStatusBarLightMode(this, true);
    }

    /**
     * 如果有需要，配置当前页面的 window的 flags 即{@link Window#addFlags(int)}
     *
     * @param window 当前页面 预览对象
     */
    @SuppressWarnings("unused")
    protected void configWindowFlags(Window window) {

    }

    /**
     * 当前页面，布局文件
     *
     * @return int
     */
    protected abstract int layoutId();

    @Override
    public Context getContext() {
        return this;
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
     * 拦截事件
     * 如果虚拟导航栏 或者状态栏 显示了，就隐藏 否则 走默认分发流程
     *
     * @param ev 事件
     * @return 拦截true 不拦截 super
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN && BarUtils.isNavBarVisible(this)) {
            BarUtils.setNavBarVisibility(this, false);
            return super.dispatchTouchEvent(ev);
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN && BarUtils.isStatusBarVisible(this)) {
            BarUtils.setStatusBarVisibility(this, false);
            return super.dispatchTouchEvent(ev);
        }
        //如果当前无人处理事件，则 隐藏输入框
//        if (ev.getAction() == MotionEvent.ACTION_UP){
//            closeInput();
//        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 拦截 回车按键 隐藏输入法
     *
     * @param event 当前按键事件
     * @return 拦截enter事件
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER ||
                event.getKeyCode() == KeyEvent.KEYCODE_NUMPAD_ENTER)) {
            closeInput();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 关闭输入法
     */
    protected void closeInput() {
        View currentFocus = getWindow().getCurrentFocus();
        if (currentFocus == null)
            return;
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果当前输入法已显示 并且绑定的是当前的view 则隐藏view
        if (imm != null && imm.isActive(currentFocus))
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        currentFocus.clearFocus();
    }

    /**
     * 隐藏虚拟按键
     */
    protected void hideBottomUI() {
        if (Build.VERSION.SDK_INT >= 30)
            hideSystemUi30();
        else {
            hideSystemUi23();
        }
    }
    @SuppressWarnings("deprecation")
    private void hideSystemUi23() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.INVISIBLE;

        uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        //解决虚拟按键弹出，无法再次隐藏的问题
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener((i) -> hideBottomUI());
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void hideSystemUi30() {
        View decorView = getWindow().getDecorView();
        if (decorView == null)
            return;
        WindowInsetsController controller = decorView.getWindowInsetsController();
        if (controller == null)
            return;
        controller.hide(WindowInsets.Type.systemBars());
        controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setDecorFitsSystemWindows(false);
    }
}
