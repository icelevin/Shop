package com.jyunmore.jm_shop.generator;

import com.jyunmore.lib_annotations.annotations.PayEntryGenerator;
import com.jyunmore.lib_core.wechat.template.WxPayEntryTemplate;

@SuppressWarnings("unused")
@PayEntryGenerator(packageName = "com.jyunmore.jm_shop", payEntryTemplate = WxPayEntryTemplate.class)
public interface WechatPayEntry {
}
