package com.lt;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lt.config.BaseUrlConfig;
import com.lt.config.TimeConfig;
import com.lt.db.entity.RequestEntity;
import com.lt.db.greendao.DaoManagerAircraft;
import com.lt.http.DecodeBodyFunction;
import com.lt.http.HttpException;
import com.lt.http.LibraryOkHttp;
import com.lt.log.LogUtils;
import com.lt.server.request.AircraftStateRequest;
import com.lt.utils.MGson;
import com.lt.utils.RxSchedulers;
import com.lt.utils.thread.ThreadPoolUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Flowable;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServiceCacheApiManager {
    private static volatile ServiceCacheApiManager manager;

    public static ServiceCacheApiManager getInstance() {
        if (manager == null)
            synchronized (ServiceCacheApiManager.class) {
                if (manager == null)
                    manager = new ServiceCacheApiManager();
            }
        return manager;
    }

    Object stateRequest = null;

    public synchronized Flowable<String> request(Object data, String path) {
        Response execute = null;
        stateRequest = data;
        Response firstResponse = null;
        Request request = null;
        try {
            //开始网络请求
            Request.Builder builder = new Request.Builder();
            RequestBody body = RequestBody.create(MGson.toJson(data), MediaType.parse("application/json"));
            request = builder.url(BaseUrlConfig.BASE_URL + path)
                    .post(body).build();
            execute = LibraryOkHttp.create().client().newBuilder().callTimeout(TimeConfig.HTTP_CALL_FLY_STATUS_TIME, TimeUnit.MILLISECONDS).build().newCall(request).execute();
            //第一次的请求结果
            firstResponse = execute;
        } catch (Exception e) {
            //网络请求失败,做缓存处理
            saveData(stateRequest, path);
            //将错误保存在文件中方便定位
            LogUtils.e("网络定位", "同步上传飞机状态失败："+e.getMessage());
            //如果访问异常就不需要向下执行了
            return Flowable.just(new Gson().toJson(new HttpException(-1, "未知错误请联系管理员")));
        }
        if (execute.code() != 200 || !execute.isSuccessful()) {
            //接口请求失败保存异常数据
            saveData(stateRequest, path);
            return Flowable.just("{}");
        }
        //拿到数据库中的所有数据
        List<RequestEntity> requestEntities = DaoManagerAircraft.getInstance().requestWeapon().loadAll();
        RequestEntity entity;
        //接口请求成功
        while (execute.code() == 200 && execute.isSuccessful() && !requestEntities.isEmpty()) {
            //缓存第一次 即 当前接口 请求的响应 用来缓存
            //移除第一位
            entity = requestEntities.remove(0);
            path = entity.requestPath;
            try {
                Request.Builder builder = new Request.Builder();
                RequestBody body = RequestBody.create(entity.requestBody, MediaType.parse("application/json"));
                request = builder.url(BaseUrlConfig.BASE_URL + entity.requestPath)
                        .post(body).build();
                execute = LibraryOkHttp.create().client().newCall(request).execute();
            } catch (Exception e) {
                //网络请求失败,做缓存处理
                saveData(stateRequest, path);
                //如果异常情况就将最开始的请求body返回回去
                return Flowable.just(retrofit2.Response.success(firstResponse.body())).flatMap(new DecodeBodyFunction<>() {
                });
            }
            if (execute.code() == 200 && execute.isSuccessful()) {
                //接口请求成功后删除掉这次的数据库中的数据
                boolean delete = DaoManagerAircraft.getInstance().requestWeapon().delete(entity);
                //删除后重新获取一次数据库,刷新数据
                requestEntities = DaoManagerAircraft.getInstance().requestWeapon().loadAll();
            } else {
                //接口没成功返回第一次请求的body
                return Flowable.just(retrofit2.Response.success(firstResponse.body())).flatMap(new DecodeBodyFunction<>() {
                });
            }

        }
        return Flowable.just(retrofit2.Response.success(firstResponse.body())).flatMap(new DecodeBodyFunction<String>() {
                }).subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    private synchronized void saveData(Object request, String path) {
        //将Request,path,time存入到数据库中
        String body = MGson.toJson(request);
        String taskType = replaceJsonField(body, "taskType", 1);
        RequestEntity requestEntity = new RequestEntity(System.currentTimeMillis(), taskType, path, String.valueOf(System.currentTimeMillis()));
        DaoManagerAircraft.getInstance().requestWeapon().insert(requestEntity);
    }
    public synchronized String replaceJsonField(String jsonString, String fieldName, int newValue) {
        try {
            // 将JSON字符串转换成JSONObject对象
            JSONObject json = new JSONObject(jsonString);
            JSONObject task = json.getJSONObject("task");
            // 替换字段的值
            task.put(fieldName, newValue);

            // 将JSONObject对象转换回字符串形式
            return json.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 出现异常则返回原始JSON字符串
        return jsonString;
    }

    /**
     * 数据补传时调用的方法
     *
     * @param entity
     * @return
     */
    public synchronized Flowable<String> requestData(RequestEntity entity, String path) {
        Response execute = null;
        Request request = null;
        try {
            //开始网络请求
            Request.Builder builder = new Request.Builder();
            String s = entity.requestBody;
            RequestBody body = RequestBody.create(s, MediaType.parse("application/json"));
            request = builder.url(BaseUrlConfig.BASE_URL + path)
                    .post(body).build();
            execute = LibraryOkHttp.create().client().newBuilder()
                    .callTimeout(TimeConfig.HTTP_CONNECT, TimeUnit.MILLISECONDS)
                    .connectTimeout(TimeConfig.HTTP_CONNECT, TimeUnit.MILLISECONDS)
                    .build().newCall(request).execute();
        } catch (Exception e) {
            LogUtils.e(e);
            //如果访问异常就不需要向下执行了
            return Flowable.just(new Gson().toJson(new HttpException(-1, e.getMessage() == null ? "未知错误" : e.getMessage())));
        }
        if (execute.code() != 200 || !execute.isSuccessful()) {
            return Flowable.just("补传失败,错误码:" + execute.code());
        }
        //接口请求成功，从数据库中删除该数据
        boolean delete = DaoManagerAircraft.getInstance().requestWeapon().delete(entity);
        LogUtils.i("补传成功,数据库删除该数据结果:"+delete);
        return Flowable.just("补传成功").subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }
}