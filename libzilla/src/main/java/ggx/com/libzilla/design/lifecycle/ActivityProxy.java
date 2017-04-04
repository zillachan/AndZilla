package ggx.com.libzilla.design.lifecycle;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author jerry.Guan
 *         created by 2017/4/4
 */

public class ActivityProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.i("hahah","方法调用前");
        return method.invoke(proxy,args);
    }
}
