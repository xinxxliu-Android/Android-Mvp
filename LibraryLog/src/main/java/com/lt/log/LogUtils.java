package com.lt.log;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

public class LogUtils {

    private static final String TAG = "[flying-log]";
    private static boolean sIsDebug = false;

    static String fileName;
    static String methodName;
    static int lineNumber;
    private static int mLog_level = 0;
    //日志级别
    private static int VERBOSE = 2;
    private static int DEBUG = 3;
    private static int INFO = 4;
    private static int WARN = 5;
    private static int ERROR = 6;
    private static int FAILTAL = 7;

    private static final HashMap<String, Long> logTextMap = new HashMap<>();

    private LogUtils() {
        /* Protect from instantiations */
    }

    /**
     * 设置日志打印等级，login之前调用，不设置默认打印等级为0
     *
     * @param log_level 日志等级0~5, 0=verbose,1=debug,2=info,3=warn,4=error 5=failtal
     */
    public static void setLogLevel(int log_level) {
        mLog_level = log_level;
    }

    /**
     * 初始化,在自定义Application类调用
     *
     * @param isDebug 是否打印log,建议传入BuildConfig.DEBUG
     *                isDebug = true 打印 且 创建日志文件   false 不打印 但是 创建日志文件
     */
    public static void initialize(Context context, boolean isDebug) {
        LogToFile.init(context);
        sIsDebug = isDebug;
    }

    /**
     * 判断是否是debug模式
     *
     * @return
     */
    public static boolean isDebuggable() {
        return sIsDebug;
    }

    /**
     * 创建log文件
     *
     * @param log
     * @return
     */
    private static String createLog(String log) {

        StringBuilder builder = new StringBuilder();
        builder.append("[" + BuildConfig.CHANNEL_KEY);
        builder.append(" " + BuildConfig.VERSION_NAME);
        builder.append("_" + BuildConfig.VERSION_CODE);
        builder.append("_" + BuildConfig.CHANNEL_TYPE +"]");
        builder.append("【");
        builder.append(fileName);//文件名
        builder.append(" -> ");
        builder.append(methodName);//方法名
        builder.append(":");
        builder.append(lineNumber);//代码行
        builder.append("】");
        builder.append(log);
        return builder.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        if (sElements != null && sElements.length > 0) {
            fileName = sElements[1].getFileName();
            methodName = sElements[1].getMethodName();
            lineNumber = sElements[1].getLineNumber();
        }
    }

    public static void e(String message) {
        if (mLog_level <= ERROR) {
            getMethodNames(new Throwable().getStackTrace());
            String log = createLog(message);
            if (isDebuggable()) {
                Log.e(TAG, log);
            } else {
                LogManager.getInstance().log(log, ERROR);
            }
            LogToFile.e(TAG, log);
        }
    }

    public static void e(Throwable e) {
        if (e == null)
            return;
        LogManager.getInstance().log(e.getMessage(), ERROR);
        e(e.getMessage(), e);
    }

    public static void e(String message, Throwable e) {
        if (mLog_level <= ERROR) {
            getMethodNames(e.getStackTrace());
            String log = createLog(message);
            if (isDebuggable()) {
                Log.e(TAG, log, e);
            } else {
                LogManager.getInstance().log(log, ERROR);
            }
            LogToFile.e(TAG, log + e.getMessage());
        }
    }


    public static void e(String filterTag, String message, Throwable e) {
        if (!filterTag.isEmpty()) {
            filterTag = "-" + filterTag;
        }
        if (mLog_level <= ERROR) {
            getMethodNames(e.getStackTrace());
            String log = createLog(message);
            if (isDebuggable()) {
                Log.e(TAG + filterTag, log, e);
            } else {
                LogManager.getInstance().log(log, ERROR);
            }
            LogToFile.e(TAG + filterTag, log + e.getMessage());
        }
    }


    public static void e(String filterTag, String message) {
        if (!filterTag.isEmpty()) {
            filterTag = "-" + filterTag;
        }
        if (mLog_level <= ERROR) {
            getMethodNames(new Throwable().getStackTrace());
            String log = createLog(message);
            if (isDebuggable()) {
                Log.e(TAG + filterTag, log);
            } else {
                LogManager.getInstance().log(log, ERROR);
            }
            LogToFile.e(TAG + filterTag, log);
        }
    }


    public static void i(String message) {
        if (mLog_level <= INFO) {
            getMethodNames(new Throwable().getStackTrace());
            String log = createLog(message);
            if (isDebuggable()) {
                Log.i(TAG, log);
            } else {
                LogManager.getInstance().log(log, INFO);
            }
            LogToFile.i(TAG, log);
        }
    }


    public static void i(String filterTag, String message) {
        if (!filterTag.isEmpty()) {
            filterTag = "-" + filterTag;
        }
        if (mLog_level <= INFO) {
            getMethodNames(new Throwable().getStackTrace());
            String log = createLog(message);
            if (isDebuggable()) {
                Log.i(TAG + filterTag, log);
            } else {
                LogManager.getInstance().log(log, INFO);
            }
            LogToFile.i(TAG + filterTag, log);
        }
    }

    public static void d(String message) {
        if (mLog_level <= DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            String log = createLog(message);
            if (isDebuggable()) {
                Log.d(TAG, log);
            } else {
                LogManager.getInstance().log(log, DEBUG);
            }
            LogToFile.d(TAG, log);
        }
    }

    public static void d(String filterTag, String message) {
        if (!filterTag.isEmpty()) {
            filterTag = "-" + filterTag;
        }

        if (mLog_level <= DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            String log = createLog(message);
            if (isDebuggable()) {
                Log.d(TAG + filterTag, log);
            } else {
                LogManager.getInstance().log(log, DEBUG);
            }
            LogToFile.d(TAG + filterTag, log);
        }
    }

    public static void d(String filterTag, String message, long timing) {
        //如果大于100清空列表
        if (logTextMap.size() > 100) {
            logTextMap.clear();
        }
        //如果包含当前message,说明打印过了
        if (logTextMap.containsKey(filterTag + message)) {
            //验证时间
            Long aLong = logTextMap.get(filterTag + message);
            if (aLong == null) {
                aLong = 0L;
            }
            //如果小于timing，那么跳出，不进行打印。
            if (System.currentTimeMillis() - aLong < timing) {
                return;
            }
        }
        logTextMap.put(filterTag + message, System.currentTimeMillis());
        if (!filterTag.isEmpty()) {
            filterTag = "-" + filterTag;
        }

        if (mLog_level <= DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            String log = createLog(message);
            if (isDebuggable()) {
                Log.d(TAG + filterTag, log);
            } else {
                LogManager.getInstance().log(log, DEBUG);
            }
            LogToFile.d(TAG + filterTag, log);
        }
    }

    public static void v(String message) {
        if (mLog_level <= VERBOSE) {
            getMethodNames(new Throwable().getStackTrace());
            String log = createLog(message);
            if (isDebuggable()) {
                Log.v(TAG, log);
            } else {
                LogManager.getInstance().log(log, VERBOSE);
            }
            LogToFile.v(TAG, log);
        }
    }

    public static void w(String message) {
        if (mLog_level <= WARN) {
            getMethodNames(new Throwable().getStackTrace());
            String log = createLog(message);
            if (isDebuggable()) {
                Log.w(TAG, log);
            } else {
                LogManager.getInstance().log(log, WARN);
            }
            LogToFile.w(TAG, log);
        }
    }

    public static void wtf(String message) {
        if (mLog_level <= FAILTAL) {
            getMethodNames(new Throwable().getStackTrace());
            String log = createLog(message);
            if (isDebuggable()) {
                Log.wtf(TAG, log);
            }
            LogToFile.wtf(TAG, log);
        }
    }
}
