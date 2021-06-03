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


######   混淆说明  ############################
# 1. 保持对应包下的包名+方法名+变量名,方法中内容会混淆
# -keep class 包名.* { *; }

# 2. 保持对应包下的 类名+public方法名 不混淆,其他都混淆
#-keep class 包名.* {public <methods>;}

###############################################



#-keep class me.jessyan.autosize.external.MyAutoSize { *; }

## 混淆规则解释  START  #################################################################

# 包名下类名不混淆，子包下的类名会混淆。方法名与变量都会混淆
#-keep class me.jessyan.autosize.expose.*

# 父包与子包下类名不混淆,方法名与变量都会混淆
#-keep class me.jessyan.autosize.**

# 保持对应包下的包名+方法名+变量名,方法中内容会混淆
-keep class com.xuanyuan.pdf.** {*;}

## 混淆规则解释  END    #################################################################

###################################################################
#                                                                 #
# 基本指令区域（没什么别的需求不需要动）      STRAT                  #
#                                                                 #
###################################################################
# 表示proguard对代码进行迭代优化的次数，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5

# 压缩用以减小应用体积，移除未被使用的类和成员，并且会在优化动作执行之后再次执行
#（因为优化后可能会再次暴露一些未被使用的类和成员）。
#-dontshrink  压缩（Shrinking）：默认开启
#-dontoptimize  关闭优化, 程序默认开启优化,混淆在优化之后

# 混淆（Obfuscation）：默认开启，增大反编译难度，类和类成员会被随机命名，除非用keep保护。
# -dontobfuscate 关闭混淆


# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames

# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses

# 这句话能够使我们的项目混淆后产生映射文件
# 包含有类名->混淆后类名的映射关系
-verbose

# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers

# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify


# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses

# 避免混淆泛型
-keepattributes Signature

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/cast,!field/*,!class/merging/*

###################################################################
#                                                                 #
# 基本指令区域（没什么别的需求不需要动）      END                    #
#                                                                 #
###################################################################




###################################################################
#                                                                 #
# Android开发中一些需要保留的公共部分（没什么别的需求不需要动）START  #
#                                                                 #
###################################################################

# 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
# 因为这些子类都有可能被外部调用

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends androidx.appcompat.app.AppCompatActivity
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View

# 保留support下的所有类及其内部类
-keep class androidx.appcompat.app.*

# 保留继承的
-keep public class * extends android.support.*

# 保留R下面的资源
-keep class **.R$* {*;}

# 保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留在Activity中的方法参数是view的方法，
# 这样一来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保留Parcelable序列化类不被混淆
-keep class * implements android.os.Parcelable {

}

# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}
###################################################################
#                                                                 #
# Android开发中一些需要保留的公共部分（没什么别的需求不需要动）  END  #
#                                                                 #
###################################################################




# webView处理，项目中没有使用到webView忽略即可
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#    public *;
#}
#-keepclassmembers class * extends android.webkit.webViewClient {
#    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
#    public boolean *(android.webkit.WebView, java.lang.String);
#}
#-keepclassmembers class * extends android.webkit.webViewClient {
#    public void *(android.webkit.webView, jav.lang.String);
#}

# 移除Log类打印各个等级日志的代码，打正式包的时候可以做为禁log使用，这里可以作为禁止log打印的功能使用
# 记得proguard-android.txt中一定不要加-dontoptimize才起作用
# 另外的一种实现方案是通过BuildConfig.DEBUG的变量来控制
#-assumenosideeffects class android.util.Log {
#    public static int v(...);
#    public static int i(...);
#    public static int w(...);
#    public static int d(...);
#    public static int e(...);
#}



