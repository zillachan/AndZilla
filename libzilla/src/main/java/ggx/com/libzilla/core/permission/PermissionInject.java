package ggx.com.libzilla.core.permission;

/**
 * Created by jerry.guan on 4/21/2017.
 */

public class PermissionInject {

    private MethodCallback callback;


    public  void callMethod(Object obj, int requestCode, boolean isSuccess){
        String fullName=obj.getClass().getName();
        if(callback==null){
            try {
                Class<?> clazz=Class.forName(fullName+"$$Authority");
                callback= (MethodCallback) clazz.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(callback!=null){
            if(isSuccess)
                callback.invoke(obj,requestCode);
            else
                callback.invokeFail(obj,requestCode);
        }
    }


}
