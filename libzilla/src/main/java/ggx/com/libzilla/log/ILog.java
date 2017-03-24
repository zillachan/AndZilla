package ggx.com.libzilla.log;

/**
 * Created by jerry.guan on 3/24/2017.
 */

public interface ILog {

    void print(String str);
    void print(String str,Throwable tr);
}
