package ggx.com.libzilla.util;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author jerry.Guan
 *         created by 2017/4/9
 */

public  interface Visitor {

    //访问者需要访问的holder
    void setLogic(BaseViewHolder holder,int position);
    //访问者需要提供的布局
    int getViewLayout();
}
