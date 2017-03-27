package ggx.com.libzilla.design.mvp;

/**
 * Created by jerry.guan on 2/28/2017.
 */

public interface BaseView2<T,J> extends BaseView{

    void getData(T data);
    void getData2(J data);
}
