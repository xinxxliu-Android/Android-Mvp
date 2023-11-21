package com.lt.func;

/**
 * 全应用 统一回调接口定义。
 * 所有 不包含进度的回调，全部使用该接口。保持统一
 * 所有 包含进度的回调，使用{@link IProgressCallBack}接口
 * @param <T>   回调数据类型
 *              注意：所有回调函数全部为主线程。 如果当前非主线程，手动切换为主线程。
 */
public interface ICallBacks<T> {
    /**
     * 回调成功
     * @param t 成功的数据
     */
    void callback(T t);

    /**
     * 回调异常。或 操作异常
     * @param t 异常对象
     */
    void failed(Throwable t);
}
