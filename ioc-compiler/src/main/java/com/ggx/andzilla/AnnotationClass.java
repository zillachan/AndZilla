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
 * 这个类用来保存被注解的对象
 * Created by jerry.guan on 4/20/2017.
 */

public class AnnotationClass {
    private static class TypeUtil {
        static final ClassName BINDER = ClassName.get("ggx.com.ioc_api", "ViewBinder");
        static final ClassName PROVIDER = ClassName.get("ggx.com.ioc_api", "ViewFinder");
    }
    private TypeElement mTypeElement;
    private ArrayList<BindViewField> fields;
    private Elements elements;

    public AnnotationClass(TypeElement mTypeElement, Elements elements) {
        this.mTypeElement = mTypeElement;
        this.elements = elements;
        fields=new ArrayList<>();
    }

    public void addField(BindViewField field){
        fields.add(field);
    }

    /**
     * 生成java文件对象
     */
    JavaFile generateFile(){
        //创建方法
        MethodSpec.Builder bindViewMethod=MethodSpec.methodBuilder("bindView")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(mTypeElement.asType()),"host")
                .addParameter(TypeName.OBJECT,"object")
                .addParameter(TypeUtil.PROVIDER,"finder");
        //在方法里添加众多控件的绑定方法
        for (BindViewField field:fields){
            bindViewMethod.addStatement("host.$N=($T)(finder.findView(object,$L))",
                    field.getFieldName(),ClassName.get(field.getFieldType()),field.getResId());
        }

        MethodSpec.Builder unBindViewMethod = MethodSpec.methodBuilder("unBindView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(TypeName.get(mTypeElement.asType()), "host")
                .addAnnotation(Override.class);
        //unbind方法目前什么都不做
        //......
        //生成类
        TypeSpec injectClass=TypeSpec.classBuilder(mTypeElement.getSimpleName()+"$$ViewBinder")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(TypeUtil.BINDER,TypeName.get(mTypeElement.asType())))
                .addMethod(bindViewMethod.build())
                .addMethod(unBindViewMethod.build())
                .build();
        String packageName=elements.getPackageOf(mTypeElement).getQualifiedName().toString();
        return JavaFile.builder(packageName,injectClass).build();

    }

}
