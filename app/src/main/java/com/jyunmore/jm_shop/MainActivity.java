package com.jyunmore.jm_shop;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.widget.Toast;

import com.jyunmore.lib_core.activitys.ProxyAcitivity;
import com.jyunmore.lib_core.app.Latte;
import com.jyunmore.lib_core.delegates.LatteDelegate;
import com.jyunmore.lib_core.ui.launcher.ILauncherLisenter;
import com.jyunmore.lib_core.ui.launcher.OnLauncherFinishTag;
import com.jyunmore.lib_ec.launcher.LauncherDelegate;
import com.jyunmore.lib_ec.login.SignInDelegate;
import com.jyunmore.lib_ec.main.EcButtomDelegate;
import com.jyunmore.lib_ec.sign.ISignListener;

public class MainActivity extends ProxyAcitivity implements ISignListener, ILauncherLisenter {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Latte.getConfigurator().withActivity(this);
    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {

    }

    @Override
    public void onSignUpSuccess() {

    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
                Toast.makeText(this, "启动结束，用户已登录", Toast.LENGTH_LONG).show();
                startWithPop(new EcButtomDelegate());
                break;
            case NOT_SIGNED:
                Toast.makeText(this, "启动结束，用户未登录", Toast.LENGTH_LONG).show();
                startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }
    }
}
