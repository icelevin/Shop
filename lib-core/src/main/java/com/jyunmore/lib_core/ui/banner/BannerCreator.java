package com.jyunmore.lib_core.ui.banner;

import android.view.View;
import android.widget.AdapterView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.jyunmore.lib_core.R;

import java.util.ArrayList;

public class BannerCreator {
    public static void setDefault( ConvenientBanner<String> convenientBanner,
                                  ArrayList<String> banners, OnItemClickListener listener) {
        convenientBanner.setPages(new HolderCreator(), banners)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(listener)
                .startTurning(3000)
                .setCanLoop(true);
    }
}
