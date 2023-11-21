package com.lt.safe;

import com.lt.PluginHttp;
import com.lt.utils.RxSchedulers;
import com.sgcc.epri.iscp.ProxySocketImplFactory;
import com.sgcc.epri.iscp.ProxySocketImplFactoryCallback;

import java.util.HashMap;
import java.util.Map;
import io.reactivex.rxjava3.core.Flowable;

/**
 * 安全连接管理器
 * 这个只是进行连接服务地址使用，不适用普通接口使用
 */
public class SafetyConnectManager {

    private final String appId = PluginHttp.mAppContext.getPackageName();

    private volatile static SafetyConnectManager mInstance;

    public static SafetyConnectManager getInstance() {
        if (null == mInstance) {
            synchronized (SafetyConnectManager.class) {
                if (null == mInstance)
                    mInstance = new SafetyConnectManager();
            }
        }
        return mInstance;
    }

    private final Map<String, ProxySocketImplFactory> socketProxyMap;

    public SafetyConnectManager() {
        socketProxyMap = new HashMap<>();
    }

    /**
     * 异步连接
     *
     * @param address x
     * @param port x
     * @param connectCallback x
     */
    public void connect(final String address, final String port, final com.lt.safe.ISafetyConnectCallback connectCallback) {
        Flowable.fromCallable(()->{
            connectBlocking(address, port, connectCallback);
            return 1;
        }).subscribeOn(RxSchedulers.request())
                        .subscribe();
    }

    /**
     * 同步连接，会阻塞当前线程，谨慎使用
     *
     * @param address x
     * @param port x
     * @param connectCallback x
     */
    public void connectBlocking(final String address, final String port, final com.lt.safe.ISafetyConnectCallback connectCallback) {
        //创建socket代理对象
        final ProxySocketImplFactory proxySocketImplFactory = new ProxySocketImplFactory();

        final String proxySocketKey = constructProxySocketKey(address, port);

        //如果之前有这个ip记录的话，将之前的断开，然后重新连接
        removeProxySocket(proxySocketKey);

        proxySocketImplFactory.init(PluginHttp.mAppContext, new ProxySocketImplFactoryCallback() {

            @Override
            public void onIscpLog(String s) {
                if (connectCallback != null) {
                    connectCallback.onIscpLog(s, address, port);
                }
            }

            @Override
            public void onIscpStop() {
                if (connectCallback != null) {
                    connectCallback.onConnectStop(address, port);
                }
            }
        });

        //判断状态
        if (proxySocketImplFactory.getStatus() == ProxySocketImplFactory.kNotConnected) {

            int iPort = Integer.parseInt(port);
            //TODO native 进行网络连接
            int result = proxySocketImplFactory.connectIscpServer(
                    address, iPort, PluginHttp.mAppContext, appId
            );

            if (result == 0) { //连接成功
                //如果连接成功，将状态进行保存
                putProxySocket(proxySocketKey, proxySocketImplFactory);
            }
            if (connectCallback != null) {
                connectCallback.onConnectResult(result == 0, address, port);
            }
        }
    }


    public synchronized void putProxySocket(String key, ProxySocketImplFactory proxySocketImplFactory) {
        socketProxyMap.put(key, proxySocketImplFactory);
    }

    public synchronized void removeProxySocket(String key) {
        if (socketProxyMap.containsKey(key)) {
            ProxySocketImplFactory proxySocketImplFactory = socketProxyMap.get(key);
            proxySocketImplFactory.disconnectISCPServer();
            socketProxyMap.remove(key);
        }
    }

    public String constructProxySocketKey(String address, String port) {
        return address + ":" + port;
    }

    public synchronized void disConnectAll() {
        for (ProxySocketImplFactory proxySocketImplFactory : socketProxyMap.values()) {
            proxySocketImplFactory.disconnectIscpServer();
        }
        socketProxyMap.clear();
    }

    public Map<String, ProxySocketImplFactory> getSocketProxyMap() {
        return socketProxyMap;
    }
}
