package com.lt.safe;

/**
 * 互联网大区连接回调
 */
public interface IConnectCallback {

    void connectSuccess();

    void connectFail();

    void connectStop();
}
