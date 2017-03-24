package ggx.com.mvpdemo.mvplib;

/**
 * Created by jerry.guan on 2/28/2017.
 */

public interface BaseView4<T,J,K,L> extends BaseView{

    void getData(T data);
    void getData2(J data);
    void getData3(K data);
    void getData4(L data);
}
