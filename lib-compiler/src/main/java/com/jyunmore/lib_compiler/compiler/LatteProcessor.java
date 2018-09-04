package com.jyunmore.lib_compiler.compiler;

import com.google.auto.service.AutoService;
import com.jyunmore.lib_annotations.annotations.AppRegisterGenerator;
import com.jyunmore.lib_annotations.annotations.EntryGenerator;
import com.jyunmore.lib_annotations.annotations.PayEntryGenerator;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;


@SuppressWarnings("unused")
@AutoService(Processor.class)
public final class LatteProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        generateEntryCode(roundEnvironment);
        generateAppRegisterCode(roundEnvironment);
        generatePayEntryCode(roundEnvironment);
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> types = new LinkedHashSet<>();
        final Set<Class<? extends Annotation>> supportAnnomations = getSupportAnnomations();
        for (Class<? extends Annotation> annotation : supportAnnomations) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    private Set<Class<? extends Annotation>> getSupportAnnomations() {
        final Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(EntryGenerator.class);
        annotations.add(AppRegisterGenerator.class);
        annotations.add(PayEntryGenerator.class);
        return annotations;
    }

    private void generateEntryCode(RoundEnvironment env) {
        final EntryVisitor entryVisitor =
                new EntryVisitor(processingEnv.getFiler());
        scan(env, EntryGenerator.class, entryVisitor);
    }

    private void generatePayEntryCode(RoundEnvironment env) {
        final PayEntryVisitor entryVisitor =
                new PayEntryVisitor(processingEnv.getFiler());
        scan(env, PayEntryGenerator.class, entryVisitor);
    }

    private void generateAppRegisterCode(RoundEnvironment env) {
        final AppRegisterVisitor entryVisitor =
                new AppRegisterVisitor(processingEnv.getFiler());
        scan(env, AppRegisterGenerator.class, entryVisitor);
    }

    private void scan(RoundEnvironment evn, Class<? extends Annotation> annotations, AnnotationValueVisitor visitor) {
        for (Element typeElement : evn.getElementsAnnotatedWith(annotations)) {
            final List<? extends AnnotationMirror> annotationMirrors = typeElement.getAnnotationMirrors();
            for (AnnotationMirror annotationMirror : annotationMirrors) {
                final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();
                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {
                    entry.getValue().accept(visitor, null);
                }
            }
        }
    }
}
