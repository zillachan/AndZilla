package ggx.com.libzilla.core.permission;

import android.util.SparseArray;

/**
 * Created by jerry.guan on 4/21/2017.
 */

public class PermissionInject {

    private SparseArray<MethodCallback> methodCallbacks=new SparseArray<>();

    public void callMethod(Object obj,int requestCode){
        String fullName=obj.getClass().getName();
        MethodCallback callback=methodCallbacks.get(requestCode);
        if(callback==null){
            try {
                Class<?> clazz=Class.forName(fullName+"$$Authority");
                callback= (MethodCallback) clazz.newInstance();
                methodCallbacks.put(requestCode,callback);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(callback!=null){
            callback.invoke(obj,requestCode);
        }
    }

}
