package ggx.com.ioc_api;

/**
 * Created by jerry.guan on 4/20/2017.
 */

public interface ViewBinder<T> {

    void bindView(T host,Object object,ViewFinder finder);
    void unBindView(T host);
}
