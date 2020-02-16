package com.example.festec.generators;
import com.example.my_annotations.PayEntryGenerator;
import com.example.my_core.wechat.templates.WXPayEntryTemplate;

@PayEntryGenerator(
        packageName = "com.example.festec",
        payEntryTemplete = WXPayEntryTemplate.class
)
public interface WeChatPayEntry {
}
