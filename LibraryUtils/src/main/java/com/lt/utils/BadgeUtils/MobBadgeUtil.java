//package com.lt.utils.BadgeUtils;
//
//import android.app.Notification;
//import android.content.Context;
//import android.os.Build;
//
//import com.blankj.utilcode.util.Utils;
//
//public class MobBadgeUtil {
//    private static final String VERSION = "1.0";
//    private static Context mContext;
//    private static BadgeManager_x mBadgeManager;
//
//    private MobBadgeUtil() {
//    }
//
//    public static void init(Context context) {
//        mContext = context;
//        mBadgeManager = new BadgeManager_x(context);
//    }
//
//    public static boolean setBadge(int number) {
//        if (mBadgeManager == null)
//            init(Utils.getApp());
//        return mBadgeManager.setBadgeNumber(null, number, "");
//    }
//
//    public static boolean addBadge(Notification notification, int number, String string, boolean isShowNotification) {
//        if (mBadgeManager == null)
//            init(Utils.getApp());
//        return mBadgeManager.setBadgeNumber(notification, number, string, isShowNotification);
//    }
//
//    public static void clearBadge() {
//        if (mBadgeManager == null)
//            init(Utils.getApp());
//        if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
//            mBadgeManager.cancelNotificationXM();
//        } else {
//            mBadgeManager.setBadgeNumber((Notification) null, 0, "");
//        }
//
//    }
//
//    public static int getBadgeNumberXM(Notification notification) {
//        return BadgeManager_x.getBadgeNumberXM(notification);
//    }
//}
