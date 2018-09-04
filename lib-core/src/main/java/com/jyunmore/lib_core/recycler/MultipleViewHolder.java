package com.jyunmore.lib_core.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

public class MultipleViewHolder extends BaseViewHolder {
    public MultipleViewHolder(View view) {
        super(view);
    }

    public static MultipleViewHolder create(View view) {
        return new MultipleViewHolder(view);
    }
}
