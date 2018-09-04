package com.jyunmore.jm_shop.generator;

import com.jyunmore.lib_annotations.annotations.EntryGenerator;
import com.jyunmore.lib_core.wechat.template.WXEntryTemplate;

@SuppressWarnings("unused")
@EntryGenerator(packageName = "com.jyunmore.jm_shop", entryTemplate = WXEntryTemplate.class)
public interface WeChatEntry {

}
