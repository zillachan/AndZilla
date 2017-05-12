package com.ggx.andzilla;

import com.ggx.andzilla.annotation.Lifecycle;
import com.google.auto.service.AutoService;
import com.google.common.base.Optional;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor6;

import static com.google.auto.common.MoreElements.getAnnotationMirror;

/**
 * Created by jerry.guan on 5/12/2017.
 */
@SupportedAnnotationTypes({"com.ggx.andzilla.annotation.Lifecycle"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@AutoService(Processor.class)
public class LifecycleProcessor extends AbstractProcessor{

    private Filer filer;
    private Elements elementsUtil;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer=processingEnvironment.getFiler();
        elementsUtil=processingEnvironment.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends TypeElement> elements=ElementFilter.typesIn(roundEnvironment.getElementsAnnotatedWith(Lifecycle.class));
        //生成类文件
        TypeSpec.Builder lifecycleManage=TypeSpec.classBuilder("LifecycleManager")
                .addSuperinterface(ClassName.get("ggx.com.libzilla.design.lifecycle","ILifecycleManager"));

        FieldSpec cycleMap=
                FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(Map.class),ClassName.get(String.class),ClassName.get("android.app.Application","ActivityLifecycleCallbacks"))
                        ,"cycleMap",Modifier.PRIVATE)
                .initializer("new $T<>()", LinkedHashMap.class)
                .build();
        lifecycleManage.addField(cycleMap);

        MethodSpec.Builder constructor=MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC);
        for(TypeElement element:elements){
            AnnotationMirror providerAnnotation = getAnnotationMirror(element, Lifecycle.class).get();
            DeclaredType providerInterface = getProviderInterface(providerAnnotation);
            TypeElement providerType = (TypeElement) providerInterface.asElement();
            constructor.addStatement("cycleMap.put($S,new $T())",element.getQualifiedName(),providerType.asType());

        }
        lifecycleManage.addMethod(constructor.build());

        MethodSpec removeLifecycle=MethodSpec.methodBuilder("removeLifecycle")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class,"key")
                .addStatement("cycleMap.remove(key)").build();
        MethodSpec getLifecycle=MethodSpec.methodBuilder("getLifecycle")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class,"key")
                .returns(ClassName.get("android.app.Application","ActivityLifecycleCallbacks"))
                .addStatement("return cycleMap.get(key)").build();
        lifecycleManage.addMethod(removeLifecycle);
        lifecycleManage.addMethod(getLifecycle);
        try {

            JavaFile.builder("com.ggx.lib.lifecycle",lifecycleManage.build()).build().writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Optional<AnnotationMirror> getAnnotationMirror(Element element,
                                                                 Class<? extends Annotation> annotationClass) {
        String annotationClassName = annotationClass.getCanonicalName();
        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            TypeElement annotationTypeElement = asType(annotationMirror.getAnnotationType().asElement());
            if (annotationTypeElement.getQualifiedName().contentEquals(annotationClassName)) {
                return Optional.of(annotationMirror);
            }
        }
        return Optional.absent();
    }
    public static TypeElement asType(Element element) {
        return element.accept(TYPE_ELEMENT_VISITOR, null);
    }

    private static final ElementVisitor<TypeElement, Void> TYPE_ELEMENT_VISITOR =
            new SimpleElementVisitor6<TypeElement, Void>() {
                @Override protected TypeElement defaultAction(Element e, Void p) {
                    throw new IllegalArgumentException();
                }

                @Override public TypeElement visitType(TypeElement e, Void p) {
                    return e;
                }
            };

    private DeclaredType getProviderInterface(AnnotationMirror providerAnnotation) {

        // The very simplest of way of doing this, is also unfortunately unworkable.
        // We'd like to do:
        //    ServiceProvider provider = e.getAnnotation(ServiceProvider.class);
        //    Class<?> providerInterface = provider.value();
        //
        // but unfortunately we can't load the arbitrary class at annotation
        // processing time. So, instead, we have to use the mirror to get at the
        // value (much more painful).

        Map<? extends ExecutableElement, ? extends AnnotationValue> valueIndex =
                providerAnnotation.getElementValues();
        AnnotationValue value = valueIndex.values().iterator().next();
        return (DeclaredType) value.getValue();
    }
}
