package com.ggx.andzilla;

import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by jerry.guan on 4/21/2017.
 */

public class AuthorityMethod {

    private ExecutableElement executableElement;

    public AuthorityMethod(ExecutableElement executableElement) {
        this.executableElement=executableElement;
    }

    /**
     * 获取方法的名字
     */
    public Name getMethodName(){
        return executableElement.getSimpleName();
    }

    /**
     * 获取方法的参数列表
     */
    public List<? extends VariableElement> getParameters(){
        return executableElement.getParameters();
    }

    /**
     * 获取方法的返回类型
     */
    public TypeMirror getReturnType(){
        return executableElement.getReturnType();
    }

}
