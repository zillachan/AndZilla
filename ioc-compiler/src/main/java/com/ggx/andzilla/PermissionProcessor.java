package com.ggx.andzilla;

import com.ggx.andzilla.annotation.AuthorityFail;
import com.ggx.andzilla.annotation.AuthorityOK;
import com.google.auto.service.AutoService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;

/**
 * Created by jerry.guan on 4/21/2017.
 */
@AutoService(Processor.class)
public class PermissionProcessor extends AbstractProcessor{

    private Filer filer;
    private Elements elements;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer=processingEnvironment.getFiler();
        elements=processingEnvironment.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes=new HashSet<>();
        annotationTypes.add(AuthorityOK.class.getCanonicalName());
        annotationTypes.add(AuthorityFail.class.getCanonicalName());
        return annotationTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        processAuthorityOK(roundEnvironment);
        return true;
    }

    private void processAuthorityOK(RoundEnvironment roundEnvironment){
        Set<? extends ExecutableElement> elements=ElementFilter.methodsIn(roundEnvironment.getElementsAnnotatedWith(AuthorityOK.class));
        for (Element element:elements){
            ExecutableElement executableElement= (ExecutableElement) element;
            System.out.println("ExecutableElement+++++++++++++++"+executableElement.getSimpleName());
            System.out.println("ExecutableElement+++++++++++++++"+executableElement.isVarArgs());
            System.out.println("ExecutableElement+++++++++++++++"+executableElement.asType());
            List<? extends VariableElement> variableElements=executableElement.getParameters();
            System.out.println("ExecutableElement+++++++has "+variableElements.size()+" parameters");
            for (VariableElement variableElement:variableElements){
                System.out.println("variableElement+++++++++++++++"+variableElement.getSimpleName());
                System.out.println("variableElement+++++++++++++++"+variableElement.asType().toString());
            }
            System.out.println("******************************************************");
            System.out.println("ExecutableElement+++++++++++++++"+executableElement.getReturnType().toString());
        }
    }
}
