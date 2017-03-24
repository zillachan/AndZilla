package ggx.com.libzilla.log;

/**
 * Created by jerry.guan on 3/21/2017.
 */

public abstract class DecoratorLog implements ILog{

    private ILog log;

    public DecoratorLog(ILog log) {
        this.log = log;
    }

    @Override
    public void print(String str) {
        log.print(str);
    }

    @Override
    public void print(String str, Throwable tr) {
        log.print(str,tr);
    }
}
