package ggx.com.libzilla.core.log;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * 日志类
 * Created by jerry.guan on 3/21/2017.
 */

public class AppLog {

    private static AbsLog log;
    private static FileLog fileLog;

    private AppLog(){}
    static {
        log=new AbsLog();
        fileLog=new FileLog(log);
    }

    //配置Debug开关
    public static void setDebug(boolean debug) {
        AppLog.log.setDebug(debug);
    }

    public static AbsLog getLog() {
        return log;
    }

    public static FileLog getfileLog() {
        return fileLog;
    }

    public static void print(Object o){
        log.i(buildMessage(String.valueOf(o)));
    }
    private static void print(double x){
        log.i(buildMessage(String.valueOf(x)));
    }
    private static void print(int x){
        log.i(buildMessage(String.valueOf(x)));
    }
    private static void print(boolean x){
        log.i(buildMessage(String.valueOf(x)));
    }
    private static void print(char x){
        log.i(buildMessage(String.valueOf(x)));
    }
    private static void print(short x){
        log.i(buildMessage(String.valueOf(x)));
    }
    private static void print(float x){
        log.i(buildMessage(String.valueOf(x)));
    }
    private static void print(long x){
        log.i(buildMessage(String.valueOf(x)));
    }
    private static void print(char[] x){
        log.i(buildMessage(String.valueOf(x)));
    }

    public static void i(String str){
        log.i(buildMessage(str));

    }

    public static void w(String str){
        log.w(buildMessage(str));
    }
    public static void d(String str){
        log.d(buildMessage(str));
    }
    public static void e(String str){
        log.e(buildMessage(str));
    }

    public static void i(String str,Throwable tr){
        log.i(buildMessage(str), tr);
    }
    public static void w(String str,Throwable tr){
        log.w(buildMessage(str), tr);
    }
    public static void d(String str,Throwable tr){
        log.d(buildMessage(str), tr);
    }
    public static void e(String str,Throwable tr){
        log.e(buildMessage(str), tr);
    }

    private static String buildMessage(String log){
        StackTraceElement caller = new Throwable().fillInStackTrace()
                .getStackTrace()[2];
        StringBuilder builder=new StringBuilder();
        builder.append(caller.toString());
        builder.append(System.getProperty("line.separator"));
        builder.append(log);
        return builder.toString();
    }

    //****************权限申请用***********************
    public static FileLog apply(AppCompatActivity appCompatActivity){
        fileLog.apply(appCompatActivity);
        return fileLog;
    }

    public static FileLog apply(Fragment fragment){
        fileLog.apply(fragment);
        return fileLog;
    }

    //******************华丽的分割线************************
}
