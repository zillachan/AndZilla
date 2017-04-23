package ggx.com.libzilla.core.permission;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 动态权限申请类
 *
 * @author jerry.Guan
 * @date 2016/9/18
 */
public class MPermission {

    private WeakReference<AppCompatActivity> activityWeakReference;
    private WeakReference<Fragment> fragmentWeakReference;
    private Context context;
    private boolean isActivity;
    private PermissionInject inject;

    private int requestCode;

    private MPermission(AppCompatActivity activity) {
        isActivity=true;
        activityWeakReference=new WeakReference<>(activity);
        context=activity.getApplicationContext();
        inject=new PermissionInject();
    }
    private MPermission(Fragment fragment){
        isActivity=false;
        fragmentWeakReference=new WeakReference<>(fragment);
        context=fragment.getActivity().getApplicationContext();
        inject=new PermissionInject();
    }

    public static MPermission with(AppCompatActivity activity) {

        return new MPermission(activity);
    }
    public static MPermission with(Fragment fragment) {

        return new MPermission(fragment);
    }

    public void apply(int requestCode,String ...permissions){
        noPermission.clear();
        this.requestCode=requestCode;
        int index=hasPermission(permissions);
        if (index!=-1) {
            String[] p=new String[noPermission.size()];
            noPermission.toArray(p);
            //当检查时发现系统不存在这个权限的时候，需要判断当前系统版本是否>=23
            if(Build.VERSION.SDK_INT>=23){
                requestPermissionApi23(p);
            }else{
                //此处模仿官方API中的方法 进行回调
                //API23一下的版本直接返回失败
                int[] grantResults = new int[permissions.length];
                for (int i = 0; i < grantResults.length; i++){
                    if(i<index){
                        grantResults[i] = PackageManager.PERMISSION_GRANTED;
                    }else {
                        grantResults[i]=PackageManager.PERMISSION_DENIED;
                    }
                }
                requestPermissionApi(grantResults,permissions);
            }
        } else {
            //权限被允许
            inject.callMethod(activityWeakReference.get(),requestCode,true);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissionApi23(String[] permissions){
        if(isActivity){
            if(activityWeakReference!=null&&activityWeakReference.get()!=null){
                activityWeakReference.get().requestPermissions(permissions,requestCode);
            }
        }else{
            if(fragmentWeakReference!=null&&fragmentWeakReference.get()!=null){
                fragmentWeakReference.get().requestPermissions(permissions,requestCode);
            }
        }
    }
    private void requestPermissionApi(int[] grantResults,String[] permissions){
        if(isActivity){
            if(activityWeakReference!=null&&activityWeakReference.get()!=null){
                activityWeakReference.get().onRequestPermissionsResult(requestCode,permissions,grantResults);
            }
        }else{
            if(fragmentWeakReference!=null&&fragmentWeakReference.get()!=null){
                fragmentWeakReference.get().onRequestPermissionsResult(requestCode,permissions,grantResults);
            }
        }
    }
    private List<String> noPermission=new ArrayList<>();
    public int hasPermission(String[] permissions) {
        int index=-1;
        for (int i=0,j=permissions.length;i<j;i++) {
            if (ActivityCompat.checkSelfPermission(context, permissions[i])
                    != PackageManager.PERMISSION_GRANTED) {
                noPermission.add(permissions[i]);
                index=i;
            }
        }
        return index;
    }

    public void onRequestPermissionsResult(Object o,int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == this.requestCode) {
            if (grantResults.length == permissions.length) {
                for (int grant : grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        inject.callMethod(o,requestCode,false);
                        return;
                    }
                }
                //权限都允许了,初始化相机
                inject.callMethod(o,requestCode,true);
            } else {
                inject.callMethod(o,requestCode,false);
            }
        }
    }

    public void recycle(){
        if(activityWeakReference!=null){
            activityWeakReference.clear();
            activityWeakReference=null;
        }
        if(fragmentWeakReference!=null){
            fragmentWeakReference.clear();
            fragmentWeakReference=null;
        }
        inject=null;
    }
}
