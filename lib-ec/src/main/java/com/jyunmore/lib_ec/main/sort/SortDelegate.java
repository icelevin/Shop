package com.jyunmore.lib_ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jyunmore.lib_core.buttom.BaseButtomDelegate;
import com.jyunmore.lib_core.buttom.ButtomItemDelegate;
import com.jyunmore.lib_ec.R;

public class SortDelegate extends ButtomItemDelegate{
    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
