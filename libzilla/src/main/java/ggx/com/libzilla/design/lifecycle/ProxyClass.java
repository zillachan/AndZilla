package ggx.com.libzilla.design.lifecycle;

import java.lang.reflect.Proxy;

/**
 * @author jerry.Guan
 *         created by 2017/4/4
 */

public class ProxyClass {

    public static Class<?> proxy(Class<?> clazz){
        return  Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},new ActivityProxy()).getClass();

    }
}
