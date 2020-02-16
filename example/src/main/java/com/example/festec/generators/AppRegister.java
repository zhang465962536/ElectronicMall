package com.example.festec.generators;

import com.example.my_annotations.AppRegisterGenerator;
import com.example.my_core.wechat.templates.WXPayEntryTemplate;

@AppRegisterGenerator(
        packageName = "com.example.festec",
        registerTemplete = WXPayEntryTemplate.class
)
public interface AppRegister {
}
