package com.lt;

import com.google.gson.Gson;
import com.lt.config.BaseUrlConfig;
import com.lt.config.TimeConfig;
import com.lt.db.entity.RequestEntity;
import com.lt.db.greendao.DaoManagerAircraft;
import com.lt.http.DecodeBodyFunction;
import com.lt.http.HttpException;
import com.lt.http.LibraryOkHttp;
import com.lt.log.LogUtils;
import com.lt.server.request.AircraftStateRequest;
import com.lt.server.request.OrderDeviceStateRequest;
import com.lt.utils.MGson;
import com.lt.utils.RxSchedulers;

import org.json.JSONException;
import org.json.JSONObject;

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
        Request request = null;
        try {
            //开始网络请求
            Request.Builder builder = new Request.Builder();
            RequestBody body = RequestBody.create(MGson.toJson(data),
                    MediaType.parse("application/json"));
            request = builder.url(BaseUrlConfig.BASE_URL + path)
                    .post(body).build();
            execute = LibraryOkHttp.create().client().newBuilder()
                    .callTimeout(TimeConfig.HTTP_CALL_FLY_STATUS_TIME, TimeUnit.MILLISECONDS)
                    .connectTimeout(TimeConfig.HTTP_CALL_FLY_STATUS_TIME, TimeUnit.MILLISECONDS)
                    .build().newCall(request).execute();
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
            return Flowable.just("回传失败");
        }
        return Flowable.just(retrofit2.Response.success(execute.body()))
                .flatMap(new DecodeBodyFunction<>() {
                });
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
        LogUtils.i("补传成功,数据库删除该数据结果:" + delete);
        return Flowable.just("补传成功").subscribeOn(RxSchedulers.request())
                .observeOn(RxSchedulers.main());
    }

    private synchronized void saveData(Object object, String path) {
        String body = "";
        if (object instanceof AircraftStateRequest) {
            AircraftStateRequest aircraftStateRequest = (AircraftStateRequest) object;
            aircraftStateRequest.task.taskType = 1;
            body = MGson.toJson(aircraftStateRequest);
        } else if (object instanceof OrderDeviceStateRequest) {
            OrderDeviceStateRequest orderDeviceStateRequest = (OrderDeviceStateRequest) object;
            orderDeviceStateRequest.taskType = 1;
            body = MGson.toJson(orderDeviceStateRequest);
        } else {
            body = MGson.toJson(object);
        }
        //将Request,path,time存入到数据库中
        RequestEntity requestEntity = new RequestEntity(System.currentTimeMillis(), body, path,
                String.valueOf(System.currentTimeMillis()));
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
}