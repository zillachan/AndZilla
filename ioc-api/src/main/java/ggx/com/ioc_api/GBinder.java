package ggx.com.ioc_api;

import android.app.Activity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 注解框架向外提供的绑定方法
 * Created by jerry.guan on 4/20/2017.
 */
public class GBinder {

    //默认的一个Activity View查找器
    private static final ActivityViewFinder activityFinder=new ActivityViewFinder();
    private static final Map<String, ViewBinder> binderMap = new LinkedHashMap<>();//管理保持管理者Map集合


    //在activity里用的绑定
    public static void bind(Activity activity){
        bind(activity,activity,activityFinder);
    }

    private static void bind(Object host,Object object,ViewFinder finder){
        //获取类名
        String className=host.getClass().getName();
        ViewBinder binder=binderMap.get(className);
        if(binder==null){
            try {
                Class<?> clazz=Class.forName(className+"$$ViewBinder");
                //通过反射创建一个新的ViewBinder类
                binder= (ViewBinder) clazz.newInstance();
                binderMap.put(className,binder);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(binder!=null){
            binder.bindView(host,object,finder);
        }
    }
}
