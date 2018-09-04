package com.jyunmore.lib_ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.jyunmore.lib_core.app.AcountManager;
import com.jyunmore.lib_core.app.IUserChecker;
import com.jyunmore.lib_core.delegates.LatteDelegate;
import com.jyunmore.lib_core.ui.launcher.ILauncherLisenter;
import com.jyunmore.lib_core.ui.launcher.OnLauncherFinishTag;
import com.jyunmore.lib_core.utils.storage.LattePreference;
import com.jyunmore.lib_core.utils.timer.BaseTimerTask;
import com.jyunmore.lib_core.utils.timer.ITimerListener;
import com.jyunmore.lib_ec.R;
import com.jyunmore.lib_ec.R2;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;


public class LauncherDelegate extends LatteDelegate implements ITimerListener {
    @BindView(R2.id.tv_timer)
    AppCompatTextView textViewn = null;
    private Timer mTimer = null;
    private int mCount = 5;
    private ILauncherLisenter launcherLisenter = null;

    @OnClick(R2.id.tv_timer)
    void onClickTimerView() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            checkIsShow();
        }
    }

    private void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask timerTask = new BaseTimerTask(this);
        mTimer.schedule(timerTask, 0, 1000);
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTimer();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherLisenter) {
            launcherLisenter = (ILauncherLisenter) activity;
        }
    }

    //判断是否滚动
    private void checkIsShow() {
        if (!LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
            start(new LauncherScrollDelegate(), SINGLETASK);
        } else {
            //检查用户是否登录app
            AcountManager.checkAcount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    if (launcherLisenter != null) {
                        launcherLisenter.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void onNotSignIn() {
                    if (launcherLisenter != null) {
                        launcherLisenter.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });
        }
    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (textViewn != null) {
                    textViewn.setText(MessageFormat.format("跳过\n{0}秒", mCount));
                    mCount--;
                    if (mCount < 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                            checkIsShow();
                        }
                    }
                }
            }
        });
    }
}
