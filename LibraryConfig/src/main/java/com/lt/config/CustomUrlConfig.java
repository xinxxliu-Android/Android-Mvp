package com.lt.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 地区特有接口路径配置类。
 * 该类声明了 当前app 所有地区的 非标准通用接口PATH
 * 用以 在调用接口时进行转换
 * 通过 LocalInterceptor进行path转换 避免多地区 声明多个 请求函数问题
 */
public interface CustomUrlConfig {
    //原path 不再使用，仅做替换使用
    String PATH_BASE = "abc";

     interface ChongQing {
        /**
         * 重庆地区
         * 请求获取飞手信息接口
         */
        @CustomUrlConfig.PathConfig(
                value = "/android/operator/info",
                encode = "com.lt.server.request.WorkerInfoRequest",
                decode = "com.lt.server.response.WorkerInfoResponse"
        )
        int PATH_WORKER_INFO = 326;

        /**
         * 重庆地区
         * app主动创建工单
         */
        @CustomUrlConfig.PathConfig(
                value = "/android/main/createWork",
                encode = "com.lt.server.request.CreateInspectRequest",
                decode = "com.lt.server.response.CreateInspectResponse"
        )
        int PATH_CREATE_INSPECT = 327;
    }


    /**
     * 注解 用以声明 并绑定
     * {@link CustomUrlConfig}中 所有常量值 对应的path、请求类、解析类
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface PathConfig {
        /**
         * 当前对象 对应的 path
         *
         * @return x 不可为空
         */
        String value();

        /**
         * 当前对象 对应的 请求对象 bean类
         * 以{@link Class#getName()} 全限定名
         * 使用时，使用{@link Class#forName(String)} 恢复为请求类型的实体数据
         *
         * @return x
         */
        String encode();

        /**
         * 当前对象 对应的 响应对象 bean类
         * 以{@link Class#getName()} 全限定名
         * 使用时，使用{@link Class#forName(String)} 恢复为响应类型的实体数据
         *
         * @return x
         */
        String decode();
    }
}
