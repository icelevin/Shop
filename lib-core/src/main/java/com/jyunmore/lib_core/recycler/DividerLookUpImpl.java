package com.jyunmore.lib_core.recycler;

import com.choices.divider.Divider;

public class DividerLookUpImpl implements BaseDecoration.DividerLookup {
    public final int COLOR;
    public final int SIZE;

    public DividerLookUpImpl(int COLOR, int SIZE) {
        this.COLOR = COLOR;
        this.SIZE = SIZE;
    }

    @Override
    public Divider getVerticalDivider(int position) {
        return new Divider.Builder().color(COLOR).size(SIZE).build();
    }

    @Override
    public Divider getHorizontalDivider(int position) {
        return new Divider.Builder().color(COLOR).size(SIZE).build();
    }
}
