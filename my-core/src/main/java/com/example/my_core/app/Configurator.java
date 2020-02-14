package com.example.my_core.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

//进行配置文件的存储 和 获取
public class Configurator {
    //使用 WeakHashMap 可以最大限度限制内存溢出   机制是 当里面的键值对不再被系统使用的时候 自行进行清除和回收
    //Configurator 配置项 是伴随整个app生命周期存在而存在的，不能使用WeakHashMap
   // private static final WeakHashMap<String, Object> LATTE_CONFIGS = new WeakHashMap<>();
    //改进
    private static final HashMap<Object, Object> LATTE_CONFIGS = new HashMap<>();
    //定义字体图标库的存储空间
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    //拦截器
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator() {
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY.name(), false);//刚开始是没有配置的 .name 是输出 false 代表没有配置
    }

    //线程安全 懒汉模式
    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    final HashMap<Object, Object> getLatteConfigs() {
        return LATTE_CONFIGS;
    }

    //静态内部类单例模式初始化
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    //配置完成之后 改变配置状态
    public final void configure() {
        //初始化图标是通用的 所以也得一开始初始化
        initIcons();
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY.name(), true);
    }

    //配置API_HOST
    public final Configurator withApiHost(String host) {
        LATTE_CONFIGS.put(ConfigKeys.API_HOST.name(), host);
        return this;
    }

    //初始化 iconify
    private void initIcons() {
        if (ICONS.size() > 0) {
            //如果已经有字体
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0)); //取出第一个字体
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    //加入自己的图标
    public final Configurator withIcon(IconFontDescriptor descriptor){
        ICONS.add(descriptor);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors ){
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }

    //检查配置项目是否完成
    private void checkConfiguration() {
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigKeys.CONFIG_READY.name());
        //如果配置没有完成
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    //SuppressWarnings 告诉编译器 这个 T 类型是没有检查过的 但是不用抛出警告
    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = LATTE_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) LATTE_CONFIGS.get(key);
    }


}
