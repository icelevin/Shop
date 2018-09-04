package com.jyunmore.lib_ec.datebase;

import com.alibaba.fastjson.JSONObject;
import com.jyunmore.lib_core.app.AcountManager;
import com.jyunmore.lib_ec.sign.ISignListener;

public class SignHandler {
    public static void onSignUp(String response, ISignListener signListener) {
        final JSONObject profileJson = JSONObject.parseObject(response);
        final String sessionToken = profileJson.getString("sessionToken");
        final String objectId = profileJson.getString("objectId");
        final String userName = profileJson.getString("username");
        final String email = profileJson.getString("email");
        final String mobilePhoneNumber = profileJson.getString("mobilePhoneNumber");
        final UserProfile userProfile = new UserProfile(objectId, email, sessionToken, userName, mobilePhoneNumber);

        DataBaseManager.getInstance().getDao().insert(userProfile);
        //注册成功跳登录
        AcountManager.setSignState(true);
        signListener.onSignUpSuccess();
    }

    public static void onSignIn(String response, ISignListener signListener) {
        final JSONObject profileJson = JSONObject.parseObject(response);
        final String sessionToken = profileJson.getString("sessionToken");
        final String objectId = profileJson.getString("objectId");
        final String userName = profileJson.getString("username");
        final String email = profileJson.getString("email");
        final String mobilePhoneNumber = profileJson.getString("mobilePhoneNumber");
        final UserProfile userProfile = new UserProfile(objectId, email, sessionToken, userName, mobilePhoneNumber);

        DataBaseManager.getInstance().getDao().insert(userProfile);
        AcountManager.setSignState(true);
        signListener.onSignInSuccess();
    }

}
