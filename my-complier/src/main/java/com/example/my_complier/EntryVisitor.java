package com.example.my_complier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;

//创建visitor 相当于属性，所注解的类，变量，方法所传入的值 去取出他们 相当于一个访问器
public final class EntryVisitor extends SimpleAnnotationValueVisitor7<Void, Void> {

    //需要遍历的东西
    private Filer mFiler = null;
    //需要循环找出的类型
    private TypeMirror mTypeMirror = null;
    //最终拿到的包名
    private String mPackgeName = null;

    public void setFiler(Filer filer) {
        this.mFiler = filer;
    }

    @Override
    public Void visitString(String s, Void p) {
        mPackgeName = s;
        return p;
    }

    //找出注解 所 注解的类 所标注的元信息 并且生成代码

    @Override
    public Void visitType(TypeMirror t, Void p) {
        mTypeMirror = t;
        generateJavaCode();
        return p;
    }

    //生成模板代码
    private void generateJavaCode() {
        //TypeSpec 生成所需要的类 Type指的是class
        final TypeSpec targeActivity =
                TypeSpec.classBuilder("WXEntryActivity")  //指定类名
                        .addModifiers(Modifier.PUBLIC) //指定该类是Public
                        .addModifiers(Modifier.FINAL) //设定为无法继承
                        .superclass(TypeName.get(mTypeMirror))  //继承于 注解中 模板类的类型
                        .build();

        //生成文件  builder()传的是 PackName 在注解写入的PaclName 再加上微信规定的API
        final JavaFile javaFile = JavaFile.builder(mPackgeName+".wxapi",targeActivity)
                .addFileComment("微信入口文件")  //添加注释
                .build();

        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
