package ggx.com.libzilla.core.permission;

/**
 * Created by jerry.guan on 4/21/2017.
 */

public interface MethodCallback<T> {

    void invoke(T source);
}
