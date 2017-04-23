package com.ggx.andzilla;

import com.ggx.andzilla.annotation.AuthorityFail;
import com.ggx.andzilla.annotation.AuthorityOK;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;

/**
 * Created by jerry.guan on 4/21/2017.
 */
@AutoService(Processor.class)
public class PermissionProcessor extends AbstractProcessor{

    private Filer filer;
    private Elements elements;
    private Map<String, PermissionClass> permissionClassMap;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer=processingEnvironment.getFiler();
        elements=processingEnvironment.getElementUtils();
        permissionClassMap=new HashMap<>();
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
        permissionClassMap.clear();
        processAuthority(roundEnvironment);
        for(PermissionClass permissionClass:permissionClassMap.values()){
            try {
                permissionClass.toFile().writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private void processAuthority(RoundEnvironment roundEnvironment){
        Set<? extends ExecutableElement> elements=ElementFilter.methodsIn(roundEnvironment.getElementsAnnotatedWith(AuthorityOK.class));
        for (ExecutableElement element:elements){
            PermissionClass clazz=getPermissionClass(element);
            AuthorityOKMethod authorityOKMethod=new AuthorityOKMethod(element);
            clazz.addOkMethod(authorityOKMethod);
//            ExecutableElement executableElement= (ExecutableElement) element;
//            System.out.println("ExecutableElement+++++++++++++++"+executableElement.getSimpleName());
//            System.out.println("ExecutableElement+++++++++++++++"+executableElement.isVarArgs());
//            System.out.println("ExecutableElement+++++++++++++++"+executableElement.asType());
//            List<? extends VariableElement> variableElements=executableElement.getParameters();
//            System.out.println("ExecutableElement+++++++has "+variableElements.size()+" parameters");
//            for (VariableElement variableElement:variableElements){
//                System.out.println("variableElement+++++++++++++++"+variableElement.getSimpleName());
//                System.out.println("variableElement+++++++++++++++"+variableElement.asType().toString());
//            }
//            System.out.println("******************************************************");
//            System.out.println("ExecutableElement+++++++++++++++"+executableElement.getReturnType().toString());
        }
        Set<? extends ExecutableElement> elementFail=ElementFilter.methodsIn(roundEnvironment.getElementsAnnotatedWith(AuthorityFail.class));
        for (ExecutableElement element:elementFail){
            PermissionClass clazz=getPermissionClass(element);
            AuthorityFailMethod authorityFailMethod=new AuthorityFailMethod(element);
            clazz.addFailMethod(authorityFailMethod);
        }
    }

    private PermissionClass getPermissionClass(ExecutableElement element){
        TypeElement typeElement= (TypeElement) element.getEnclosingElement();
        String fullName=typeElement.getQualifiedName().toString();
        PermissionClass clazz=permissionClassMap.get(fullName);
        if(clazz==null){
            clazz=new PermissionClass(typeElement,elements);
            permissionClassMap.put(fullName,clazz);
        }
        return clazz;
    }
}
