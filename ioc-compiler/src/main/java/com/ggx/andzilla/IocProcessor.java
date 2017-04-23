package com.ggx.andzilla;

import com.ggx.andzilla.annotation.BindView;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by jerry.guan on 3/28/2017.
 */
@AutoService(Processor.class)
public class IocProcessor extends AbstractProcessor{

    private Filer mFileUtils;//文件相关辅助类
    private Elements mElementUtils;//元素相关辅助类
    private Messager mMessager;//日志相关辅助类

    private Map<String, AnnotationClass> mAnnotatedClassMap;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFileUtils=processingEnvironment.getFiler();
        mElementUtils=processingEnvironment.getElementUtils();
        mMessager=processingEnvironment.getMessager();
        mAnnotatedClassMap=new TreeMap<>();
    }

    //add support annotations
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<>();
        annotationTypes.add(BindView.class.getCanonicalName());
        return annotationTypes;
    }

    //core
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mAnnotatedClassMap.clear();
        processBindView(roundEnvironment);
//        //循环所有创建好的AnnotationClass对象然后创建文件
//        for(AnnotationClass annotationClass:mAnnotatedClassMap.values()){
//            try {
//                annotationClass.generateFile().writeTo(System.out);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return true;
    }

    //do process BindView annotation
    private void processBindView(RoundEnvironment roundEnvironment){
        Set<? extends Element> elements=roundEnvironment.getElementsAnnotatedWith(BindView.class);
        for (Element element:elements){
            AnnotationClass annotationClass=getAnnotationClass(element);
            //创建此注解标注的所有字段对象
            BindViewField bindViewField=new BindViewField(element);
            annotationClass.addField(bindViewField);
        }

    }

    private AnnotationClass getAnnotationClass(Element element){
        //获取所在的类元素（TypeElement）
        TypeElement typeElement= (TypeElement) element.getEnclosingElement();
        String fullName=typeElement.getQualifiedName().toString();
        AnnotationClass annotationClass=mAnnotatedClassMap.get(fullName);
        if(annotationClass==null){
            annotationClass=new AnnotationClass(typeElement,mElementUtils);
            mAnnotatedClassMap.put(fullName,annotationClass);
        }
        return annotationClass;
    }
}
