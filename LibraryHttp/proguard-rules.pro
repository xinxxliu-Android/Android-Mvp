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

#retrofit混淆
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

#rxjava 混淆
-dontwarn java.util.concurrent.Flow*

#自定义混淆
-keep class com.lt.entity.HttpEntity{<fields>;}