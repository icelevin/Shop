package com.jyunmore.lib_core.ui.banner;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class ImageHolder extends Holder<String> {
    private AppCompatImageView imageView;

    public ImageHolder(View itemView) {
        super(itemView);
        imageView = new AppCompatImageView(itemView.getContext());

    }

    @Override
    protected void initView(View itemView) {
    }

    @Override
    public void updateUI(String data) {
        Glide.with(itemView.getContext())
                .load(data)
                .apply(new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate())
                .into(imageView);
    }
}
