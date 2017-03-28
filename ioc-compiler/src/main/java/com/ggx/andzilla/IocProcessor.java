package com.ggx.andzilla;

import com.ggx.andzilla.annotation.BindView;
import com.google.auto.service.AutoService;

import java.util.LinkedHashSet;
import java.util.Set;

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

    private Filer mFileUtils;
    private Elements mElementUtils;
    private Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFileUtils=processingEnvironment.getFiler();
        mElementUtils=processingEnvironment.getElementUtils();
        mMessager=processingEnvironment.getMessager();
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
        Set<? extends Element> elements=roundEnvironment.getElementsAnnotatedWith(BindView.class);
        for (Element element:elements){
            //field type
            VariableElement variableElement= (VariableElement) element;
            //class type
            TypeElement typeElement= (TypeElement) variableElement.getEnclosingElement();
            String qualifiedName=typeElement.getQualifiedName().toString();
            System.out.println("qualifiedName#######################################="+qualifiedName);
        }
        return true;
    }
}
