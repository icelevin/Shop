package com.jyunmore.lib_compiler.compiler;


import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;

final class PayEntryVisitor extends SimpleAnnotationValueVisitor7<Void, Void> {
    private Filer filer = null;
    private String packageName;

    PayEntryVisitor(Filer filer) {
        this.filer = filer;
    }


    @Override
    public Void visitString(String s, Void aVoid) {
        packageName = s;
        return aVoid;
    }

    @Override
    public Void visitType(TypeMirror typeMirror, Void aVoid) {
        generateJavaCode(typeMirror);
        return aVoid;
    }

    private void generateJavaCode(TypeMirror typeMirror) {
        final TypeSpec targetActivity = TypeSpec.classBuilder("WXPayEntryActivity")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.FINAL)
                .superclass(TypeName.get(typeMirror))
                .build();
        final JavaFile javaFile = JavaFile.builder(packageName + ".wxapi", targetActivity)
                .build();

        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
