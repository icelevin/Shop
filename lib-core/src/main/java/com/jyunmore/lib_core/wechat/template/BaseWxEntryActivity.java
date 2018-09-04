package com.jyunmore.lib_core.wechat.template;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jyunmore.lib_core.net.RestClient;
import com.jyunmore.lib_core.net.callback.IError;
import com.jyunmore.lib_core.net.callback.IFailure;
import com.jyunmore.lib_core.net.callback.ISuccess;
import com.jyunmore.lib_core.wechat.BaseWxActivity;
import com.jyunmore.lib_core.wechat.LatteWeChat;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

public abstract class BaseWxEntryActivity extends BaseWxActivity {
    //用户登录成功后回调
    protected abstract void onSignInSuccess(String userInfo);

    //微信发送到第三方应用的回调
    @Override
    public void onReq(BaseReq baseReq) {

    }

    //第三方应用发送请求微信后的回调
    @Override
    public void onResp(BaseResp baseResp) {
        final String code = ((SendAuth.Resp) baseResp).code;
        final StringBuilder authUrl = new StringBuilder();
        authUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(LatteWeChat.APP_ID)
                .append("&secret=")
                .append(LatteWeChat.APP_SECERT)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        Log.e("authUrl", authUrl.toString());
        getAuth(authUrl.toString());
    }

    private void getAuth(final String authUrl) {
        RestClient
                .builder()
                .url(authUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject jsonObject = JSON.parseObject(response);
                        final String accessToken = jsonObject.getString("access_token");
                        final String openId = jsonObject.getString("openid");
                        final StringBuilder userinfoUrl = new StringBuilder();
                        userinfoUrl
                                .append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                                .append(accessToken)
                                .append("&openid=")
                                .append(openId)
                                .append("&lang=")
                                .append("zh_CN");
                        Log.e("userinfoUrl", userinfoUrl.toString());
                        getUserinfoUrl(userinfoUrl.toString());

                    }
                })
                .builder()
                .get();
    }

    private void getUserinfoUrl(String userinfoUrl) {
        RestClient
                .builder()
                .url(userinfoUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        onSignInSuccess(response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .builder()
                .get();
    }
}
