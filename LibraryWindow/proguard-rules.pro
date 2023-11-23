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

#指定优化、压缩级别
-optimizationpasses 10
-overloadaggressively
-flattenpackagehierarchy
#指定package模糊字典
-packageobfuscationdictionary rules_20.txt
-keep class android.annotation.*
# 保持native方法不被混淆
-keepclasseswithmembernames class * { native <methods>;}

#ButterKnife 混淆-------------------
-keep class butterknife.* { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {@butterknife.* <fields>;}
-keepclasseswithmembernames class * {@butterknife.* <methods>;}
#ButterKnife 混淆-------------------结束