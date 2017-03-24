package ggx.com.libzilla.log;

import android.content.Context;
import android.util.Log;

/**
 * 用来调用者使用
 * Created by jerry.guan on 3/21/2017.
 */

public class AppLog {

    public static AbsLog log;
    public static FileLog file;

    private AppLog(){}
    static {
        log=new AbsLog();
        file=new FileLog(log);
    }

    //配置Debug开关
    public static void setDebug(boolean debug) {
        AppLog.log.setDebug(debug);
    }

    public static AbsLog getLog() {
        return log;
    }

    public static void setFileLog(FileLog fileLog){
        file=fileLog;
    }

    private static void checkLog(){
        if(log==null){
            log=new AbsLog();
        }
    }

    public static void print(Object o){
        i(String.valueOf(o));
    }
    private static void print(double x){
        i(String.valueOf(x));
    }
    private static void print(int x){
        i(String.valueOf(x));
    }
    private static void print(boolean x){
        i(String.valueOf(x));
    }
    private static void print(char x){
        i(String.valueOf(x));
    }
    private static void print(short x){
        i(String.valueOf(x));
    }
    private static void print(float x){
        i(String.valueOf(x));
    }
    private static void print(long x){
        i(String.valueOf(x));
    }
    private static void print(char[] x){
        i(String.valueOf(x));
    }

    public static void i(String str){
        checkLog();
        log.i(buildMessage(str,null));

    }

    public static void w(String str){
        checkLog();
        log.w(buildMessage(str,null));
    }
    public static void d(String str){
        checkLog();
        log.d(buildMessage(str,null));
    }
    public static void e(String str){
        checkLog();
        log.e(buildMessage(str,null));
    }

    public static void i(String str,Throwable tr){
        checkLog();
        log.i(buildMessage(str), tr);
    }
    public static void w(String str,Throwable tr){
        checkLog();
        log.w(buildMessage(str), tr);
    }
    public static void d(String str,Throwable tr){
        checkLog();
        log.d(buildMessage(str), tr);
    }
    public static void e(String str,Throwable tr){
        checkLog();
        log.e(buildMessage(str), tr);
    }
    private static String buildMessage(String log,Throwable tr){
        StackTraceElement caller = new Throwable().fillInStackTrace()
                .getStackTrace()[2];
        StringBuilder builder=new StringBuilder();
        builder.append(caller.getClassName());
        builder.append(".");
        builder.append(caller.getMethodName());
        builder.append("( ");
        builder.append(caller.getFileName());
        builder.append(": ");
        builder.append(caller.getLineNumber());
        builder.append(") ");
        builder.append(System.getProperty("line.separator"));
        builder.append(log);
        if (tr != null) {
            builder.append(System.getProperty("line.separator"));
            //获取异常栈的内容
            builder.append(Log.getStackTraceString(tr));
        }
        return builder.toString();
    }
    private static String buildMessage(String log){
        StackTraceElement caller = new Throwable().fillInStackTrace()
                .getStackTrace()[2];
        StringBuilder builder=new StringBuilder();
        builder.append(caller.getClassName());
        builder.append(".");
        builder.append(caller.getMethodName());
        builder.append("( ");
        builder.append(caller.getFileName());
        builder.append(": ");
        builder.append(caller.getLineNumber());
        builder.append(") ");
        builder.append(System.getProperty("line.separator"));
        builder.append(log);
        return builder.toString();
    }

    //**********************下面注册程序异常处理******************************
    public static void registCrashHandler(Context context){
        CrashHandler.getInstance().init(context);
    }
}
