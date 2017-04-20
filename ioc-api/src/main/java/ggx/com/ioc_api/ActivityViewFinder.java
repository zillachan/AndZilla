package ggx.com.ioc_api;

import android.app.Activity;
import android.view.View;

/**
 * 提供一个默认的view查找者
 * Created by jerry.guan on 4/20/2017.
 */

public class ActivityViewFinder implements ViewFinder{


    @Override
    public View findView(Object object, int id) {

        return ((Activity)object).findViewById(id);
    }
}
