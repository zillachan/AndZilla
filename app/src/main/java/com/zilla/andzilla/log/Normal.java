package com.zilla.andzilla.log;

import android.view.View;
import android.widget.TextView;

import com.zilla.andzilla.R;

import ggx.com.libzilla.util.BaseViewHolder;
import ggx.com.libzilla.util.Visitor;

/**
 * @author jerry.Guan
 *         created by 2017/4/10
 */

public class Normal implements Visitor{
    @Override
    public BaseViewHolder createViewHolder(View itemView) {
        return new BaseViewHolder<Person>(itemView) {
            TextView tv;
            @Override
            public void setLogic( Person model, int position, int itemType) {
                tv.setText(model.name);
            }

            @Override
            public void initView(View itemView) {
                tv= (TextView) itemView.findViewById(R.id.t1);
            }
        };
    }

    @Override
    public int getViewLayout() {
        return R.layout.normal_item;
    }
}
