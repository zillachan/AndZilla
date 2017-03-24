package ggx.com.libzilla.log;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

/**
 * Created by jerry.guan on 3/21/2017.
 */

public class FileLog extends DecoratorLog{

    private WeakReference<AppCompatActivity> activityWeakReference;
    private WeakReference<Fragment> fragmentWeakReference;
    private boolean isAvtivity;
    private static final int REQUEST_CODE=0x10;
    private String str;
    private Throwable throwable;

    public FileLog(ILog log, AppCompatActivity activity) {
        super(log);
        activityWeakReference=new WeakReference<>(activity);
        isAvtivity=true;
    }

    public FileLog(ILog log, Fragment fragment) {
        super(log);
        fragmentWeakReference=new WeakReference<>(fragment);
        isAvtivity=false;
    }

    public FileLog(ILog log) {
        super(log);
    }

    private boolean checkPermission(AppCompatActivity appCompatActivity){
        int check=ActivityCompat.checkSelfPermission(appCompatActivity.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(check!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activityWeakReference.get(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
            return false;
        }
        return true;
    }

    private boolean checkPermission(Fragment fragment){
        int check=ActivityCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(check!= PackageManager.PERMISSION_GRANTED){
            fragmentWeakReference.get().requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
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

    public void recycle(){
        if(isAvtivity){
            if(activityWeakReference!=null){
                activityWeakReference.clear();
                activityWeakReference=null;
            }
        }else {
            if(fragmentWeakReference!=null){
                fragmentWeakReference.clear();
                fragmentWeakReference=null;
            }
        }
    }
    private boolean isPermissionGranted(){
        if(isAvtivity){
            if(activityWeakReference!=null&&activityWeakReference.get()!=null){
                return checkPermission(activityWeakReference.get());
            }
        }else {
            if(fragmentWeakReference!=null&&fragmentWeakReference.get()!=null){
                return checkPermission(fragmentWeakReference.get());
            }
        }
        return false;
    }

    @Override
    public void print(String str) {
        super.print(str);
        this.str=str;
        this.throwable=null;
        if(isPermissionGranted()){
            writeToFile(str,throwable);
        }
    }

    @Override
    public void print(String str, Throwable tr) {
        super.print(str, tr);
        this.str=str;
        this.throwable=null;
        if(isPermissionGranted()){
            writeToFile(str,throwable);
        }
    }

    private void writeToFile(String str, Throwable tr){
        System.out.println("文件写入完成");
    }

}
