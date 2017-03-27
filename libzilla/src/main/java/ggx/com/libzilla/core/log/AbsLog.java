package ggx.com.libzilla.core.log;

import android.util.Log;

/**
 * Created by jerry.guan on 3/21/2017.
 */

public class AbsLog implements ILog{

    private static final String tag="AppLog";
    protected boolean debug=true;

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void i(String log){
        i(log,null);
    }

    public void w(String log){
        w(log,null);
    }

    public void d(String log){
        d(log,null);
    }

    public void e(String log){
        e(log,null);
    }

    public void i(String log,Throwable tr){
        if(debug){
            if(tr==null)
                Log.i(tag,log);
            else
                Log.i(tag,log,tr);
        }
    }
    public void w(String log,Throwable tr){
        if(debug){
            if(tr==null)
                Log.w(tag,log);
            else
                Log.w(tag,log,tr);
        }
    }

    public void d(String log,Throwable tr){
        if(debug){
            if(tr==null)
                Log.d(tag,log);
            else
                Log.d(tag,log,tr);
        }
    }

    public void e(String log,Throwable tr){
        if(debug){
            if(tr==null)
                Log.e(tag,log);
            else
                Log.e(tag,log,tr);
        }
    }


    @Override
    public void print(String str) {
        if(debug){
            Log.i(tag,str);
        }
    }

    @Override
    public void print(String str, Throwable tr) {
        if(debug){
            Log.i(tag,str,tr);
        }
    }
}
