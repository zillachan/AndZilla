package com.zilla.andzilla.log;

import ggx.com.libzilla.util.ItemModel;

/**
 * @author jerry.Guan
 *         created by 2017/4/10
 */

public class Person implements ItemModel{

    public String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public int getItemType() {
        return 0;
    }
}
