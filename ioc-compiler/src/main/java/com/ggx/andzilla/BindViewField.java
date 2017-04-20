package com.ggx.andzilla;

import com.ggx.andzilla.annotation.BindView;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * 这个类用来保存被注解的成员变量
 * Created by jerry.guan on 4/20/2017.
 */

public class BindViewField {

    //被注解的成员属性
    private VariableElement variableElement;
    //注解中给定的资源值
    private int resId;

    public BindViewField(Element element) {
        //如果被注解的对象不是成员属性则抛出一个异常
        if(element.getKind() != ElementKind.FIELD){
            throw new IllegalArgumentException(String.format("Only fields can be annotation with @%s", BindView.class.getSimpleName()));
        }
        variableElement= (VariableElement) element;
        //获取字段上的注解
        BindView bindView=variableElement.getAnnotation(BindView.class);
        resId=bindView.value();
    }

    /**
     * 获取字段的名字
     * @return
     */
    Name getFieldName(){
        return variableElement.getSimpleName();
    }

    /**
     * 获取变量上标注的id
     */
    int getResId(){
        return resId;
    }

    /**
     * 获取变量的类型
     */
    TypeMirror getFieldType(){
        return variableElement.asType();
    }


}
