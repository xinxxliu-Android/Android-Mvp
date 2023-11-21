package com.lt.func;

import androidx.annotation.NonNull;

/**
 * 全应用 统一回调接口定义。
 * 所有 包含进度的回调，全部使用该接口。保持统一
 * 所有 不包含进度的回调，使用{@link ICallBacks}接口
 * @param <T>   回调数据类型
 *              注意：所有回调函数全部为主线程。 如果当前非主线程，手动切换为主线程。
 */
@SuppressWarnings("unused")
public interface IProgressCallBack<T> {
    /**
     * 回调成功  操作成功
     * @param t 成功回调的数据体
     */
    void callBack(@NonNull T t);

    /**
     * 当前执行的进度。
     * @param percent   百分比数字
     * @param percentMsg    进度字符串消息。 防止部分情况回调时，附加字符串消息
     */
    void progress(@NonNull int percent,@NonNull String percentMsg);

    /**
     * 操作失败     执行失败
     * @param thr       失败异常对象
     */
    void failed(@NonNull Throwable thr);

    /**
     * 操作取消
     * @param cancelMsg 取消附加消息
     */
    void cancel(@NonNull String cancelMsg);
}
