package com.jyunmore.lib_ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.jyunmore.lib_core.delegates.LatteDelegate;
import com.jyunmore.lib_core.ui.LatteLoader;
import com.jyunmore.lib_ec.R;
import com.jyunmore.lib_ec.R2;
import com.jyunmore.lib_ec.datebase.SignHandler;
import com.jyunmore.lib_ec.login.SignInDelegate;

import butterknife.BindView;
import butterknife.OnClick;

public class SignUpDelegate extends LatteDelegate {

    @BindView(R2.id.et_name)
    TextInputEditText et_name;

    @BindView(R2.id.et_email)
    TextInputEditText et_email;

    @BindView(R2.id.et_phone)
    TextInputEditText et_phone;

    @BindView(R2.id.et_pwd)
    TextInputEditText et_pwd;

    @BindView(R2.id.et_rePwd)
    TextInputEditText et_rePwd;

    @BindView(R2.id.btn_sign)
    Button btn_sign;

    @BindView(R2.id.tv_login)
    AppCompatTextView tv_login;

    @OnClick(R2.id.tv_login)
    void onClickSignIn() {
        start(new SignInDelegate());
    }


    private ISignListener signListener = null;

    @OnClick(R2.id.btn_sign)
    void onClickSign() {
        if (checkForm()) {
            LatteLoader.showLoading(getContext());
            final AVUser avUser = new AVUser();
            avUser.setEmail(et_email.getText().toString());
            avUser.setMobilePhoneNumber(et_phone.getText().toString());
            avUser.setPassword(et_pwd.getText().toString());
            avUser.setUsername(et_name.getText().toString());
            avUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(AVException e) {
                    LatteLoader.stopLoading();
                    if (e == null) {
                        Toast.makeText(getContext(), "注册成功,请登录", Toast.LENGTH_LONG).show();
                        onClickSignIn();
                        SignHandler.onSignUp(avUser.toJSONObject().toString(), signListener);

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

    private boolean checkForm() {
        boolean isPass = true;
        final String name = et_name.getText().toString();
        final String email = et_email.getText().toString();
        final String phone = et_phone.getText().toString();
        final String pwd = et_pwd.getText().toString();
        final String rePwd = et_rePwd.getText().toString();

        if (name.isEmpty()) {
            et_name.setError("请输入姓名");
            isPass = false;
        } else {
            et_name.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("错误的邮箱地址");
            isPass = false;
        } else {
            et_email.setError(null);
        }
        if (phone.isEmpty() || phone.length() != 11) {
            et_phone.setError("错误的电话号码");
            isPass = false;
        } else {
            et_phone.setError(null);

        }
        if (pwd.isEmpty() || pwd.length() < 6) {
            et_pwd.setError("密码的位数小于6");
            isPass = false;
        } else {
            et_pwd.setError(null);
        }
        if (rePwd.isEmpty() || rePwd.length() < 6 || !pwd.equals(rePwd)) {
            et_rePwd.setError("密码的位数小于6");
            isPass = false;
        } else {
            et_rePwd.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sigeup;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
