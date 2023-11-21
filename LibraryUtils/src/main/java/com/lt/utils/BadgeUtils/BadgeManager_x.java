//package com.lt.utils.BadgeUtils;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.content.pm.ResolveInfo;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.core.app.NotificationCompat;
//
//
//import com.lt.utils.R;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.List;
//
//public class BadgeManager_x {
//    private Context mContext;
//    private String manufacturer;
//
//
//    public BadgeManager_x(Context context) {
//        this.manufacturer = Build.MANUFACTURER;
//        this.mContext = context;
//    }
//
//    public boolean setBadgeNumber(Notification notification, int number, String string) {
//        return setBadgeNumber(notification, number, string, true);
//    }
//
//    public boolean setBadgeNumber(Notification notification, int number, String string, boolean isShowNotification) {
//        if (number < 0) {
//            number = 0;
//        }
//
//        if (this.manufacturer.equalsIgnoreCase("Huawei")) {
//            return setBadgeNumberHW(this.mContext, number);
//        } else {
//            NotificationManager notificationManager = null;
//            if (this.manufacturer.equalsIgnoreCase("Xiaomi")) {
//                if (isShowNotification) {
//                    return setBadgeNumberSamSung(this.mContext, number) && setNotificationBadgeXiaomi(this.mContext, number, string);
//                } else {
//                    return setBadgeNumberSamSung(this.mContext, number);
//                }
//
//            } else if (this.manufacturer.equalsIgnoreCase("vivo")) {
//                return setBadgeNumberVivo(this.mContext, number);
//            } else if (this.manufacturer.equalsIgnoreCase("OPPO")) {
//                return setBadgeNumberOPPO(this.mContext, number);
//            } else if (this.manufacturer.equalsIgnoreCase("GiONEE")) {
//                return false;
//            } else if (this.manufacturer.equalsIgnoreCase("samsung")) {
//                if (isShowNotification) {
//                    return setBadgeNumberSamSung(this.mContext, number) && setNotificationBadge(this.mContext, number, string);
//                } else {
//                    return setBadgeNumberSamSung(this.mContext, number);
//                }
//
//            } else if (this.manufacturer.equalsIgnoreCase("Zuk")) {
//                return false;
//            } else if (this.manufacturer.equalsIgnoreCase("Htc")) {
//                return false;
//            } else if (this.manufacturer.startsWith("LG")) {
//                return setBadgeNumberLG(this.mContext, number);
//            } else if (this.manufacturer.equalsIgnoreCase("Lemobile")) {
//                if (notification != null) {
//                    notificationManager = (NotificationManager) this.mContext.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//                    notificationManager.notify(100, notification);
//                    return true;
//                } else {
//                    return false;
//                }
//            } else {
//                return false;
//            }
//        }
//    }
//
//    public void cancelNotificationXM() {
//        NotificationManager notificationManager = (NotificationManager) this.mContext.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.cancel(0);
//    }
//
//    public static boolean setBadgeNumberHW(Context context, int number) {
//        try {
//            Bundle bundle = new Bundle();
//            bundle.putString("package", context.getPackageName());
//            String launchClassName = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName();
//            bundle.putString("class", launchClassName);
//            bundle.putInt("badgenumber", number);
//            context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", (String) null, bundle);
//            return true;
//        } catch (Exception var4) {
//            var4.printStackTrace();
//            return false;
//        }
//    }
//
//    public static int getBadgeNumberXM(Notification notification) {
//        int number = 0;
//
//        try {
//            Field field = notification.getClass().getDeclaredField("extraNotification");
//            Object extraNotification = field.get(notification);
//            Method method = extraNotification.getClass().getDeclaredMethod("getMessageCount");
//            number = (Integer) method.invoke(extraNotification);
//            Log.d("yyhuang", "number = " + number);
//        } catch (Exception var5) {
//            var5.printStackTrace();
//        }
//
//        return number;
//    }
//
//    public static boolean setNotificationBadgeXiaomi(Context context, int count, String string) {
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        if (notificationManager == null) {
//            return false;
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            // 8.0之后添加角标需要NotificationChannel
//            NotificationChannel channel = new NotificationChannel("badge", "badge", NotificationManager.IMPORTANCE_DEFAULT);
//            channel.setShowBadge(true);
//            notificationManager.createNotificationChannel(channel);
//        }
//        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//        Notification notification = new NotificationCompat.Builder(context, "badge")
//                .setContentTitle(count + "个未读通知")
//                .setContentText(string + "")
//                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.app_icon))
//                .setSmallIcon(R.drawable.app_icon)
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
//                .setChannelId("badge")
//                .setNumber(count)
//                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL).build();
//
//        try {
//            Field field = notification.getClass().getDeclaredField("extraNotification");
//            Object extraNotification = field.get(notification);
//            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
//            method.invoke(extraNotification, count);
//        } catch (Exception e) {
//        }
//
//        if (count > 0) {
//            notificationManager.notify(notificationId, notification);
//        } else {
//            notificationManager.cancel(notificationId);
//        }
//        return true;
//    }
//
//    public static boolean setBadgeNumberSamSung(Context context, int number) {
//        try {
//            Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
//            intent.putExtra("badge_count", number);
//            intent.putExtra("badge_count_package_name", context.getPackageName());
//            String paS = context.getPackageName();
//            String launcherClassName = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName();
//            intent.putExtra("badge_count_class_name", launcherClassName);
//            context.sendBroadcast(intent);
//            return true;
//        } catch (Exception var4) {
//            var4.printStackTrace();
//            return false;
//        }
//    }
//
//    private static int notificationId = 0x12;
//
//    public static boolean setNotificationBadge(Context context, int count, String string) {
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService
//                (Context.NOTIFICATION_SERVICE);
//        if (notificationManager == null) {
//            return false;
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            // 8.0之后添加角标需要NotificationChannel
//            NotificationChannel channel = new NotificationChannel("badge", "badge",
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            channel.setShowBadge(true);
//            notificationManager.createNotificationChannel(channel);
//        }
//        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//        Notification notification = new NotificationCompat.Builder(context, "badge")
//                .setContentTitle(count + "个未接来电")
//                .setContentText(string + "")
//                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.app_icon))
//                .setSmallIcon(R.drawable.app_icon)
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
//                .setChannelId("badge")
//                .setNumber(count)
//                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL).build();
//        if (count > 0) {
//            notificationManager.notify(notificationId, notification);
//        } else {
//            notificationManager.cancel(notificationId);
//        }
//        return true;
//    }
//
//    public static boolean setBadgeNumberLG(Context context, int number) {
//        try {
//            Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
//            intent.putExtra("badge_count", number);
//            intent.putExtra("badge_count_package_name", context.getPackageName());
//            String launcherClassName = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName();
//            intent.putExtra("badge_count_class_name", launcherClassName);
//            context.sendBroadcast(intent);
//            return true;
//        } catch (Exception var4) {
//            var4.printStackTrace();
//            return false;
//        }
//    }
//
//    public static boolean setBadgeNumberVivo(Context context, int number) {
//        try {
//            Intent intent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
//            intent.putExtra("packageName", context.getPackageName());
//            String launchClassName = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName();
//            intent.putExtra("className", launchClassName);
//            intent.putExtra("notificationNum", number);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//                intent.addFlags(0x01000000);
//            context.sendBroadcast(intent);
//            return true;
//        } catch (Exception var4) {
//            return false;
//        }
//    }
//
//    public static boolean setBadgeNumberOPPO(Context context, int number) {
//        try {
//            Bundle extras = new Bundle();
//            extras.putInt("app_badge_count", number);
//            context.getContentResolver().call(Uri.parse("content://com.android.badge/badge"), "setAppBadgeCount", (String) null, extras);
//        } catch (Exception var3) {
//            var3.printStackTrace();
//        }
//
//        return true;
//    }
//
//    public static boolean canResolveBroadcast(Context context, Intent intent) {
//        PackageManager packageManager = context.getPackageManager();
//        List<ResolveInfo> receivers = packageManager.queryBroadcastReceivers(intent, 0);
//        return receivers != null && receivers.size() > 0;
//    }
//}
