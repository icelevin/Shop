package com.jyunmore.lib_ec.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.joanzapata.iconify.widget.IconTextView;
import com.jyunmore.lib_core.delegates.LatteDelegate;
import com.jyunmore.lib_core.wechat.IWeChatSignInCallback;
import com.jyunmore.lib_core.wechat.LatteWeChat;
import com.jyunmore.lib_ec.R;
import com.jyunmore.lib_ec.R2;
import com.jyunmore.lib_ec.datebase.SignHandler;
import com.jyunmore.lib_ec.sign.ISignListener;
import com.jyunmore.lib_ec.sign.SignUpDelegate;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInDelegate extends LatteDelegate {
    @BindView(R2.id.et_email)
    TextInputEditText et_email;
    @BindView(R2.id.et_pwd)
    TextInputEditText et_pwd;
    @BindView(R2.id.btn_sign)
    AppCompatButton btn_sign;
    @BindView(R2.id.tv_login)
    AppCompatTextView tv_login;
    @BindView(R2.id.icon_wx)
    IconTextView icon_wx;

    @OnClick(R2.id.icon_wx)
    void onClickWxSignIn() {
        LatteWeChat
                .getInstance()
                .onSignSuccess(new IWeChatSignInCallback() {
                    @Override
                    public void onSignInSuccess(String userInfo) {

                    }
                })
                .signIn();
    }

    private ISignListener signListener = null;

    @OnClick(R2.id.tv_login)
    void onClickSignUp() {
        start(new SignUpDelegate());
    }

    @OnClick(R2.id.btn_sign)
    void onClickSignIn() {
        if (checkForm()) {
            AVUser.logInInBackground(et_email.getText().toString(), et_pwd.getText().toString(), new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e == null) {
                        Log.e("avUser==>", avUser.toJSONObject().toString());
                        Toast.makeText(getContext(), "登录成功", Toast.LENGTH_SHORT).show();
                        SignHandler.onSignIn(avUser.toJSONObject().toString(), signListener);
                    }
                }
            });
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            signListener = (ISignListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    private boolean checkForm() {
        boolean isPass = true;
        final String email = et_email.getText().toString();
        final String pwd = et_pwd.getText().toString();


        if (email.isEmpty()) {
            et_email.setError("错误的邮箱地址");
            isPass = false;
        } else {
            et_email.setError(null);
        }
        if (pwd.isEmpty() || pwd.length() < 6) {
            et_pwd.setError("密码的位数小于6");
            isPass = false;
        } else {
            et_pwd.setError(null);
        }

        return isPass;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
