package com.jyunmore.lib_core.wechat;

import android.app.Activity;

import com.jyunmore.lib_core.app.ConfigKeys;
import com.jyunmore.lib_core.app.Latte;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class LatteWeChat {
    public static final String APP_ID = Latte.getConfigurations(ConfigKeys.WE_CHAT_APP_ID);
    public static final String APP_SECERT = Latte.getConfigurations(ConfigKeys.WE_CHAT_APP_SECRET);
    private IWXAPI iwxapi;
    private IWeChatSignInCallback mSignInCallback = null;

    private static final class Holder {
        private static final LatteWeChat INSTANCE = new LatteWeChat();
    }

    public static LatteWeChat getInstance() {
        return Holder.INSTANCE;
    }

    private LatteWeChat() {
        final Activity activity = Latte.getConfigurations(ConfigKeys.ACTIVITY);
        iwxapi = WXAPIFactory.createWXAPI(activity, APP_ID, true);
        iwxapi.registerApp(APP_ID);
    }


    public LatteWeChat onSignSuccess(IWeChatSignInCallback callback) {
        this.mSignInCallback = callback;
        return this;
    }

    public IWeChatSignInCallback getmSignInCallback() {
        return mSignInCallback;
    }

    public IWXAPI getWXAPI() {
        return iwxapi;
    }

    public final void signIn() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "random_state";
        iwxapi.sendReq(req);
    }
}
