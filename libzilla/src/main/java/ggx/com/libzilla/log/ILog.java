package ggx.com.libzilla.log;

/**
 * Created by jerry.guan on 3/24/2017.
 */

public interface ILog {

    void print(String x);
    void print(String x,Throwable tr);
}
