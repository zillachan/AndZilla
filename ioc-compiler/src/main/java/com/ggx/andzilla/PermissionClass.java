package com.ggx.andzilla;

import java.util.ArrayList;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by jerry.guan on 4/21/2017.
 */

public class PermissionClass {


    //注解所在的类元素
    private TypeElement typeElement;

    private Elements elements;
    private ArrayList<ExecutableElement> executableElements;

    public PermissionClass(TypeElement typeElement, Elements elements) {
        this.typeElement = typeElement;
        this.elements = elements;
        executableElements=new ArrayList<>();
    }
}
