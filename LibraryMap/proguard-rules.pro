# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#OSMDdroid 混淆开始
-keep public class * extends android.view.View{
    public <init>(android.content.Context);
    public <init>(android.content.Context,android.util.AttributeSet);
    public <init>(android.content.Context,android.util.AttributeSet,int);
    public void set*(...);
}
-keep class microsoft.mappoint.**{*;}
-keep class org.osmdroid.**{*;}
-keep class org.metalev.multitouch.controller.**{*;}
#osmdroid混淆结束

#高德定位混淆开始
-keep class com.amap.api.location.** { *; }
-dontwarn com.amap.api.**
-keep class com.amap.api.fence.** { *; }
-keep class com.loc.**{*;}
-dontwarn com.loc.**
-keep class com.autonavi.aps.amapapi.model.** { *; }
-dontwarn com.autonavi.aps.**
-keep class com.amap.api.maps.model.** { *; }
#高德定位混淆结束
#定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}

#搜索
-keep   class com.amap.api.services.**{*;}

#2D地图
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}

#导航
-keep class com.amap.api.navi.**{*;}
-keep class com.autonavi.**{*;}
