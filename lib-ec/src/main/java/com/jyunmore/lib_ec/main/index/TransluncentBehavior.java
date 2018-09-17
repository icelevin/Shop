package com.jyunmore.lib_ec.main.index;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.jyunmore.lib_core.ui.RgbValue;
import com.jyunmore.lib_ec.R;
@SuppressWarnings("unused")
public class TransluncentBehavior extends CoordinatorLayout.Behavior<Toolbar> {
    //顶部距离
    private int mDistanceY = 0;
    //变化速率
    private static final int SHOW_SPEED = 3;

    //定义变化的颜色
    private final RgbValue rgbValue = RgbValue.create(255, 124, 2);


    public TransluncentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        return dependency.getId() == R.id.rl_view;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return true;

    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        //增加滑动距离
        mDistanceY += dy;
        //toolbar高度

        final int targetHeight = child.getBottom();
        //当滑动时  距离小于toolbar
        if (mDistanceY > 0 && mDistanceY <= targetHeight) {
            final float scale = (float) mDistanceY / targetHeight;
            final float alpha = scale * 255;
            child.setBackgroundColor(Color.argb((int) alpha, rgbValue.red(), rgbValue.green(), rgbValue.blue()));

        } else if (mDistanceY > targetHeight) {
            child.setBackgroundColor(Color.rgb(rgbValue.red(), rgbValue.green(), rgbValue.blue()));
        }
    }
}
