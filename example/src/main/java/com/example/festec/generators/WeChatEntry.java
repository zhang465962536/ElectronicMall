package com.example.festec.generators;
import com.example.my_annotations.EntryGenerator;
import com.example.my_core.wechat.templates.WXEntryTemplate;

@EntryGenerator(
        packageName = "com.example.festec",
        entryTemplete = WXEntryTemplate.class
)
public interface WeChatEntry {
}
