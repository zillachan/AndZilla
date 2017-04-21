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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<Integer,Method> okMap;
    private Map<Integer,Method> failMap;

    private int requestCode;

    private MPermission(AppCompatActivity activity) {
        isActivity=true;
        activityWeakReference=new WeakReference<>(activity);
        context=activity.getApplicationContext();
        okMap=new HashMap<>();
        failMap=new HashMap<>();
        findMethod(activity);
    }
    private MPermission(Fragment fragment){
        isActivity=false;
        fragmentWeakReference=new WeakReference<>(fragment);
        context=fragment.getActivity().getApplicationContext();
        okMap=new HashMap<>();
        failMap=new HashMap<>();
        findMethod(fragment);
    }


    private void findMethod(Object o) {
        Method[] methods = o.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(PermissionOK.class)) {
                PermissionOK ok=method.getAnnotation(PermissionOK.class);
                Integer id=Integer.valueOf(ok.id());
                if(!okMap.containsKey(id)){
                    okMap.put(id,method);
                }
            }else if(method.isAnnotationPresent(PermissionFail.class)){
                PermissionFail fail=method.getAnnotation(PermissionFail.class);
                Integer id=Integer.valueOf(fail.id());
                if(!failMap.containsKey(id)){
                    failMap.put(id,method);
                }
            }
        }
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
            Method method=okMap.get(Integer.valueOf(requestCode));
            invoke(isActivity?activityWeakReference.get():fragmentWeakReference.get(),method);
        }
    }

    private void invoke(Object o,Method method){
        if(method==null){
            return;
        }
        method.setAccessible(true);
        try {
            method.invoke(o,new Object[]{});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissionApi23(String[] permissions){
        if(isActivity){
            activityWeakReference.get().requestPermissions(permissions,requestCode);
        }else{
            fragmentWeakReference.get().requestPermissions(permissions,requestCode);
        }
    }
    private void requestPermissionApi(int[] grantResults,String[] permissions){

        if(isActivity){
            activityWeakReference.get().onRequestPermissionsResult(requestCode,permissions,grantResults);
        }else{
            fragmentWeakReference.get().onRequestPermissionsResult(requestCode,permissions,grantResults);
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
                        Method fail=failMap.get(Integer.valueOf(requestCode));
                        invoke(o,fail);
                        return;
                    }
                }
                //权限都允许了,初始化相机
                Method ok=okMap.get(Integer.valueOf(requestCode));
                invoke(o,ok);
            } else {
                Method fail=failMap.get(Integer.valueOf(requestCode));
                invoke(o,fail);
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
    }
}
