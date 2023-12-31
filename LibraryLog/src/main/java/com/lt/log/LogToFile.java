package com.lt.log;

import android.content.Context;

import com.blankj.utilcode.util.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LogToFile {

    public static final String TAG = "[flying-log]";
    //log日志存放路径
    public static String logPath = null;
    //日期格式
    private static SimpleDateFormat dateFormatLog = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    //日期格式
    private static SimpleDateFormat dateFormatFile = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    public static final String savePath = "/LogFile/Logs";
    public static List<File> mFileList = new ArrayList<>();
    //因为log日志是使用日期命名的，使用静态成员变量主要是为了在整个程序运行期间只存在一个.log文件中
    private static Date date = new Date();
    private static Context mContent;
    //日志文件保留时长  默认7天
    public static int FILE_SAVE_DAYS = 7;

    /**
     * 设置日志文件保留时长
     *
     * @param saveDays
     */
    public static void setFileSaveDays(int saveDays) {
        FILE_SAVE_DAYS = saveDays;
    }

    /**
     * 获取日志文件保留时长
     *
     * @return
     */
    public static int getFileSaveDays() {
        return FILE_SAVE_DAYS;
    }

    /**
     * 获得文件存储路径
     * 当Context为Application时，会导致获取路径的方法空指针
     *
     * @return
     */
    private static String getFilePath(Context context) {
        //File dir = new File(context.getExternalCacheDir().getAbsoluteFile() + savePath);
        if (context == null) {
            return "";
        }
        File dir = new File(context.getExternalFilesDir(null).getAbsolutePath() + savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getPath();//存当前app的文件夹里
    }

    /**
     * 初始化，须在使用之前设置，最好在Application创建时调用
     *
     * @param context
     */
    public static void init(Context context) {
        mContent = context;
        logPath = getFilePath(context);//获得文件储存路径
    }

    /**
     * 文件是否存在
     *
     * @param context
     * @return
     */
    public static boolean isExistsFile(Context context) {
        File dir = new File(context.getExternalCacheDir().getAbsoluteFile() + savePath);
        if (!dir.exists()) {
            return false;
        } else {
            return true;
        }
    }

    private static final char VERBOSE = 'v';

    private static final char DEBUG = 'd';

    private static final char INFO = 'i';

    private static final char WARN = 'w';

    private static final char ERROR = 'e';

    private static final char WTF = 'f';

    public static void v(String tag, String msg) {
        writeToFile(VERBOSE, tag, msg);
    }

    public static void d(String tag, String msg) {
        writeToFile(DEBUG, tag, msg);
    }

    public static void i(String tag, String msg) {
        writeToFile(INFO, tag, msg);
    }

    public static void w(String tag, String msg) {
        writeToFile(WARN, tag, msg);
    }

    public static void e(String tag, String msg) {
        writeToFile(ERROR, tag, msg);
    }

    public static void wtf(String tag, String msg) {
        writeToFile(WTF, tag, msg);
    }

    private static int fileIndex = 0;

    /**
     * 将log信息写入文件中
     *
     * @param type
     * @param tag
     * @param msg
     */
    private static void writeToFile(char type, String tag, String msg) {
        logPath = getFilePath(mContent);
        if (null == logPath) {
            android.util.Log.e(TAG, "logPath == null ，未初始化LogToFile");
            return;
        }
        //如果父路径不存在
        File logFile = new File(logPath);
        if (!logFile.exists()) {
            logFile.mkdirs();//创建父路径
        }

        //log日志名，使用时间命名，保证不重复  以天为单位  每天都有新的日志文件生成
        logFile = new File(logFile + "/log_" + dateFormatFile.format(date) + "_ " + fileIndex + ".log");
        long length = FileUtils.getLength(logFile);
        if (length > 0) {
            if (length > 20 * 1024 * 1024) {
                fileIndex++;
            }
        }
        //log日志内容，可以自行定制
        String log = dateFormatLog.format(new Date()) + " " + type + " " + " " + Thread.currentThread().getId() + " " + tag + "\n" + "" + msg + "\n";
        //FileOutputStream会自动调用底层的close()方法，不用关闭
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            //这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
            fos = new FileOutputStream(logFile, true);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    //关闭缓冲流
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取.log文件列表
     *
     * @return
     */
    public static List<File> getFileList() {
        logPath = getFilePath(mContent);
        if (null == logPath) {
            android.util.Log.e(TAG, "logPath == null ，未初始化LogToFile");
            return null;
        }
        mFileList.clear();
        File file = new File(logPath);
        File[] tempList = file.listFiles();
        long length = tempList.length;
        if (length == 0) {
            android.util.Log.e(TAG, "没有日志文件");
            return null;
        }
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                String fileName = tempList[i].getName();
                if (fileName.endsWith(".log")) {
                    mFileList.add(tempList[i]);
                }
            }
        }
        return mFileList;
    }

    /**
     * 是否删除日志文件
     *
     * @return true delete  false no_delete
     */
    public static boolean isDeleteLogFile() {
        List<File> fileList = getFileList();
        if (fileList == null)
            return false;
        if (fileList.size() > getFileSaveDays()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除日志文件
     */
    public static void deleteFile() {
        List<File> fileList = getFileList();
        if (fileList == null) return;
        if (fileList.size() > getFileSaveDays()) {
            for (File file : fileList) {
                if (file.exists()) {
                    String fileName = file.getName();
                    String format = dateFormatFile.format(date);
                    String fileNameTimes = fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf(".log"));
                    if (!format.equals(fileNameTimes)) {
                        file.delete();
                    }
                }
            }
        }
    }
}