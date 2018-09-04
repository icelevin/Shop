package com.jyunmore.lib_ec.launcher;

import android.app.Activity;
import android.content.pm.LauncherApps;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.jyunmore.lib_core.app.AcountManager;
import com.jyunmore.lib_core.app.IUserChecker;
import com.jyunmore.lib_core.delegates.LatteDelegate;
import com.jyunmore.lib_core.ui.launcher.ILauncherLisenter;
import com.jyunmore.lib_core.ui.launcher.LauncherHolderCreator;
import com.jyunmore.lib_core.ui.launcher.OnLauncherFinishTag;
import com.jyunmore.lib_core.utils.storage.LattePreference;
import com.jyunmore.lib_ec.R;

import java.util.ArrayList;

public class LauncherScrollDelegate extends LatteDelegate implements OnItemClickListener {
    private ConvenientBanner<Integer> mConvenientBanner = null;
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();
    private ILauncherLisenter launcherLisenter = null;

    private void initBanner() {
        INTEGERS.add(R.mipmap.banner_1);
        INTEGERS.add(R.mipmap.banner_2);
        INTEGERS.add(R.mipmap.banner_3);
        INTEGERS.add(R.mipmap.banner_4);
        INTEGERS.add(R.mipmap.banner_5);
        mConvenientBanner.setPages(
                new LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(true);
    }

    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initBanner();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherLisenter) {
            launcherLisenter = (ILauncherLisenter) activity;
        }
    }

    @Override
    public void onItemClick(int position) {
        //如果点击的是最后一个
        if (position == INTEGERS.size() - 1) {
            LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);
            //检查是否已经登录
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
}
