package com.zilla.andzilla.log;

import ggx.com.libzilla.util.ItemModel;

/**
 * @author jerry.Guan
 *         created by 2017/4/10
 */

public class Person implements ItemModel{

    public String name;
    public int flag;

    public Person(int flag,String name) {
        this.flag=flag;
        this.name = name;
    }

    @Override
    public int getItemType() {
        return flag;
    }
}
