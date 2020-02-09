package com.example.my_core.app;

import java.util.WeakHashMap;

//进行配置文件的存储 和 获取
public class Configurator {
    //使用 WeakHashMap 可以最大限度限制内存溢出
    private static final WeakHashMap<String,Object> LATTE_CONFIGS = new WeakHashMap<>();

    private Configurator(){
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(),false);//刚开始是没有配置的 .name 是输出 false 代表没有配置
    }

    //线程安全 懒汉模式
    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

     final WeakHashMap<String, Object> getLatteConfigs() {
        return LATTE_CONFIGS;
    }

    //静态内部类单例模式初始化
    private static class Holder{
        private static final Configurator INSTANCE = new Configurator();
    }

    //配置完成之后 改变配置状态
    public final void configure(){
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(),true);
    }

    //配置API_HOST
    public final Configurator withApiHost(String host){
        LATTE_CONFIGS.put(ConfigType.API_HOST.name(),host);
        return this;
    }

    //检查配置项目是否完成
    private void checkConfiguration(){
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigType.CONFIG_READY.name());
        //如果配置没有完成
        if(!isReady){
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    //SuppressWarnings 告诉编译器 这个 T 类型是没有检查过的 但是不用抛出警告
    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Enum<ConfigType> key){
        checkConfiguration();
        return (T) LATTE_CONFIGS.get(key.name());
    }


}
