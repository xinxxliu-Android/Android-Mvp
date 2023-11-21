package com.lt

import com.lt.log.LogUtils
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

internal class CustomHttpLoggingInterceptor(level: HttpLoggingInterceptor.Level) : Interceptor {
    var tag = "网络请求"
    private val logger = HttpLoggingInterceptor.Logger { message ->
        //在此拦截日志，进行打印或者输出到文件
        LogUtils.i(tag, message)
    }

    private val loggingInterceptor = HttpLoggingInterceptor(logger).setLevel(level)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        // 记录请求信息
//        logRequest(request)

        tag = if (request.url.toString().contains("appReceiveAircraft")) {
            "定位"
        } else {
            "网络请求"
        }
        // 记录响应信息
//        val responseBuilder = originalResponse.newBuilder()
//        logResponse(originalResponse)

        return loggingInterceptor.intercept(chain)
    }

//    private fun logRequest(request: okhttp3.Request) {
//        val requestBody = request.body
//        val hasRequestBody = requestBody != null
//        val method = request.method
//        val requestHeaders = request.headers
//
//        val requestMessage = StringBuilder()
//            .append("--> $method ${request.url}\n")
//            .append("--> Headers: $requestHeaders\n")
//
//        if (hasRequestBody) {
//            val buffer = Buffer()
//            requestBody!!.writeTo(buffer)
//            requestMessage.append("--> Body: ${buffer.readUtf8()}\n")
//        }
//
//        logger.log(requestMessage.toString())
//    }
//
//    private fun logResponse(response: Response) {
//        val responseBody = response.body
//        val hasResponseBody = responseBody != null
//
//        val responseMessage = StringBuilder()
//            .append("<-- ${response.code} ${response.message} ${response.request.url}\n")
//            .append("<-- Headers: ${response.headers}\n")
//
//        if (hasResponseBody) {
//            // 克隆原始响应主体以避免过早关闭
//            val originalResponseBody: BufferedSource = response.body!!.source().buffer.clone()
//            val responseBodyString = originalResponseBody.readUtf8()
//            responseMessage.append("<-- Body: $responseBodyString\n")
//
//            logger.log(responseMessage.toString())
//
//            // 恢复原始响应的响应正文
//            val newResponseBody = responseBodyString.toResponseBody(responseBody?.contentType())
//            response.newBuilder().body(newResponseBody).build()
//        } else {
//            logger.log(responseMessage.toString())
//        }
//    }
}
