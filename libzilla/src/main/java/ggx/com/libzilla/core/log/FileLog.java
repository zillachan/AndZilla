package ggx.com.libzilla.core.log;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jerry.guan on 3/21/2017.
 */

public class FileLog implements ILog{

    private static final int REQUEST_CODE=0x2222;
    private ILog log;
    private String str;
    private Throwable throwable;
    private boolean hasPermission;

    public FileLog(ILog log) {
        this.log=log;
    }

    private boolean checkPermission(AppCompatActivity appCompatActivity){
        int check=ActivityCompat.checkSelfPermission(appCompatActivity.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(check!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(appCompatActivity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
            return false;
        }
        return true;
    }

    private boolean checkPermission(Fragment fragment){
        int check=ActivityCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(check!= PackageManager.PERMISSION_GRANTED){
            fragment.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                writeToFile(str,throwable);
            }
        }
    }

    @Override
    public void print(String str) {
        write(buildMessage(str));
    }
    private static String buildMessage(String log){
        StackTraceElement caller = new Throwable().fillInStackTrace()
                .getStackTrace()[2];
        StringBuffer builder=new StringBuffer();
        builder.append(caller.toString());
        builder.append(System.getProperty("line.separator"));
        builder.append(log);
        return builder.toString();
    }
    @Override
    public void print(String str, Throwable tr) {
        write(buildMessage(str),tr);
    }
    public void print(int x) {
        write(buildMessage(String.valueOf(x)));
    }
    public void print(int x,Throwable tr){
        write(buildMessage(String.valueOf(x)),tr);
    }

    public void print(long x) {
        write(buildMessage(String.valueOf(x)));
    }
    public void print(long x,Throwable tr){
        write(buildMessage(String.valueOf(x)),tr);
    }

    public void print(short x) {
        write(buildMessage(String.valueOf(x)));
    }
    public void print(short x,Throwable tr){
        write(buildMessage(String.valueOf(x)),tr);
    }

    public void print(char x) {
        write(buildMessage(String.valueOf(x)));
    }
    public void print(char x,Throwable tr){
        write(buildMessage(String.valueOf(x)),tr);
    }

    public void print(float x) {
        write(buildMessage(String.valueOf(x)));
    }
    public void print(float x,Throwable tr){
        write(buildMessage(String.valueOf(x)),tr);
    }

    public void print(char[] x) {
        write(buildMessage(String.valueOf(x)));
    }
    public void print(char[] x,Throwable tr){
        write(buildMessage(String.valueOf(x)),tr);
    }

    public void print(double x) {
        write(buildMessage(String.valueOf(x)));
    }
    public void print(double x,Throwable tr){
        write(buildMessage(String.valueOf(x)),tr);
    }

    public void print(Object x) {
        write(buildMessage(String.valueOf(x)));
    }
    public void print(Object x,Throwable tr){
        write(buildMessage(String.valueOf(x)),tr);
    }

    private void write(String str,Throwable tr){
        log.print(str, tr);
        this.str=str;
        this.throwable=tr;
        if(hasPermission) {
            writeToFile(str, throwable);
        }
    }
    private void write(String str){
        log.print(str);
        this.str=str;
        if(hasPermission) {
            writeToFile(str, null);
        }
    }

    public void apply(AppCompatActivity activityCompat){
        hasPermission=checkPermission(activityCompat);
    }

    public void apply(Fragment fragment){
        hasPermission=checkPermission(fragment);
    }

    private void writeToFile(String str, Throwable tr){
        System.out.println("文件写入完成");
    }

}
