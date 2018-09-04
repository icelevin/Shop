package com.jyunmore.lib_core.ui.launcher;


import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.jyunmore.lib_core.R;

public class LauncherHolder extends Holder<Integer> {
    private AppCompatImageView imageView;

    public LauncherHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView(View itemView) {
        imageView =itemView.findViewById(R.id.iv_banner);
    }

    @Override
    public void updateUI(Integer data) {
        imageView.setBackgroundResource(data);
    }
}
