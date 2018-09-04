package com.jyunmore.lib_core.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.jyunmore.lib_core.R;
import com.jyunmore.lib_core.utils.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class LatteLoader {
    private static final int LOADING_SIZE_SCALE = 8;
    private static final int LOADING_OFFSET_SCALE = 10;
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    private static final String DEFAULT_STYLE = LoaderStyle.BallBeatIndicator.name();

    public static void showLoading(Context icontext, Enum<LoaderStyle> itype) {
        showLoading(icontext, itype.name());
    }

    public static void showLoading(Context context, String type) {
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        dialog.setContentView(avLoadingIndicatorView);
        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();
        final Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = deviceWidth / LOADING_SIZE_SCALE;
            attributes.height = deviceHeight / LOADING_SIZE_SCALE;
            attributes.height = attributes.height + deviceHeight / LOADING_OFFSET_SCALE;
            attributes.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();

    }

    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_STYLE);
    }

    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        }
    }
}
