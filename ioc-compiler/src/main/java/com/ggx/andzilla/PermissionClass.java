package com.ggx.andzilla;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by jerry.guan on 4/21/2017.
 */

public class PermissionClass {


    //注解所在的类元素
    private TypeElement typeElement;

    private Elements elements;
    private ArrayList<AuthorityOKMethod> okMethods;
    private ArrayList<AuthorityFailMethod> failMethods;

    public PermissionClass(TypeElement typeElement, Elements elements) {
        this.typeElement = typeElement;
        this.elements = elements;
        okMethods=new ArrayList<>();
        failMethods=new ArrayList<>();
    }

    public void addOkMethod(AuthorityOKMethod executableElement){
        okMethods.add(executableElement);
    }

    public void addFailMethod(AuthorityFailMethod failMethod){
        failMethods.add(failMethod);
    }

    public JavaFile toFile(){
        //创建调用成功的绑定方法
        MethodSpec.Builder invokeOK=MethodSpec.methodBuilder("invoke")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                //添加方法中的参数类型为注解所在类的类型
                .addParameter(TypeName.get(typeElement.asType()),"source")
                .addParameter(TypeName.INT,"requestCode");
        for (AuthorityOKMethod method:okMethods){
            if(method.getParameters().isEmpty()){
                invokeOK.addStatement("if(requestCode==$L){" +
                        "source.$N();return;" +
                        "}",method.getRequestCode(),method.getName());
            }else {

            }
        }
        //创建调用失败的绑定方法
        MethodSpec.Builder invokeFail=MethodSpec.methodBuilder("invokeFail")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(typeElement.asType()),"source")
                .addParameter(TypeName.INT,"requestCode");
        for (AuthorityFailMethod method:failMethods){
            if(method.getParameters().isEmpty()){
                invokeFail.addStatement("if(requestCode==$L){" +
                        "source.$N();return;}",method.getRequestCode(),method.getName());
            }
        }

        TypeSpec injectClass=TypeSpec.classBuilder(typeElement.getSimpleName()+"$$Authority")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get("ggx.com.libzilla.core.permission","MethodCallback"),ClassName.get(typeElement.asType())))
                .addMethod(invokeOK.build())
                .addMethod(invokeFail.build()).build();
        String packageName=elements.getPackageOf(typeElement).getQualifiedName().toString();

        return JavaFile.builder(packageName,injectClass).build();
    }
}
