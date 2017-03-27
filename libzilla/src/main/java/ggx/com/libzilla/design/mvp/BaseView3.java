package ggx.com.libzilla.design.mvp;

/**
 * Created by jerry.guan on 2/28/2017.
 */

public interface BaseView3<T,J,K> extends BaseView{

    void getData(T data);
    void getData2(J data);
    void getData3(K data);
}
