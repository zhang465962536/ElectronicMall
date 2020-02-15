package com.example.my_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//用于传入包名，微信所需要的模板代码 绕过微信的限制 不需要在 主程序创建Activity
@Target(ElementType.TYPE)  //通知编译器 该 注解是用于类上
@Retention(RetentionPolicy.SOURCE) //通知编译器 处理该注解时 在源码阶段处理的 即打包成APK或者运行的时候不再使用他
//好处是 对性能 没有影响
public @interface EntryGenerator {

    String packageName();

    Class<?> entryTemplete();

}
