package com.example.my_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)  //通知编译器 该 注解是用于类上
@Retention(RetentionPolicy.SOURCE) //通知编译器 处理该注解时 在源码阶段处理的 即打包成APK或者运行的时候不再使用他
public @interface PayEntryGenerator {
    String packageName();

    Class<?> payEntryTemplete();

}
