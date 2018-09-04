package com.jyunmore.lib_core.wechat.template;

import com.jyunmore.lib_core.wechat.LatteWeChat;

//微信登录后返回的activity
public class WXEntryTemplate extends BaseWxEntryActivity {
    @Override
    protected void onResume() {
        super.onResume();
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onSignInSuccess(String userInfo) {
        LatteWeChat.getInstance().getmSignInCallback().onSignInSuccess(userInfo);
    }

}
