package com.lt.http;

import com.lt.utils.RxSchedulers;
import java.util.HashMap;

import io.reactivex.rxjava3.core.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 网络请求 示例
 */
public class HttpExample {
    public static void main(String[] args) {
        //添加请求头
        LibraryHttp.addHeaders(new HashMap<>());
        //添加自定义拦截器 注意，必须在首次实例化retrofit之前调用，否则无效
        LibraryHttp.addInterceptor(chain -> chain.proceed(chain.request()));
        AServiceApi build = LibraryHttp.retrofitRequest().build(AServiceApi.class);
        build.requestExample()
                .flatMap(new DecodeBodyFunction<String>(){})
                .observeOn(RxSchedulers.main())
                .subscribeOn(RxSchedulers.request())
                .subscribe(new EntitySubscriber<String>() {
                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
     interface AServiceApi{
        @POST()
        @FormUrlEncoded
        Flowable<Response<ResponseBody>> requestExample();
    }
}
