package com.jyunmore.lib_core.ui.banner;

import android.view.View;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.jyunmore.lib_core.R;

public class HolderCreator implements CBViewHolderCreator{
    @Override
    public Holder createHolder(View itemView) {
        return new ImageHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_multi_banner;
    }
}
