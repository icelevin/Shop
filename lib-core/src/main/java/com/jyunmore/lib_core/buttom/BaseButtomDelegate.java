package com.jyunmore.lib_core.buttom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.joanzapata.iconify.widget.IconTextView;
import com.jyunmore.lib_core.R;
import com.jyunmore.lib_core.R2;
import com.jyunmore.lib_core.delegates.LatteDelegate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseButtomDelegate extends LatteDelegate implements View.OnClickListener {

    private final ArrayList<ButtomItemDelegate> ITEM_DELEGATES = new ArrayList<>();
    private final ArrayList<ButtomTabBean> ITEMS_BEANS = new ArrayList<>();
    private final LinkedHashMap<ButtomTabBean, ButtomItemDelegate> ITEMS = new LinkedHashMap<>();

    private int mCurrentDelegate = 0;
    private int mIndexDelegate = 0;
    private int mClickColor = Color.RED;

    @BindView(R2.id.buttom_bar)
    LinearLayoutCompat buttom_bar;

    public abstract LinkedHashMap<ButtomTabBean, ButtomItemDelegate> setItems(ItemBuilder builder);

    public abstract int setIndexDelegate();

    @ColorInt
    public abstract int setClickColor();

    @Override
    public Object setLayout() {
        return R.layout.delegate_buttom;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentDelegate = setIndexDelegate();
        if (setClickColor() != 0) {
            mClickColor = setClickColor();
        }
        final ItemBuilder builder = ItemBuilder.builder();
        final LinkedHashMap<ButtomTabBean, ButtomItemDelegate> items = setItems(builder);
        ITEMS.putAll(items);

        for (Map.Entry<ButtomTabBean, ButtomItemDelegate> item : ITEMS.entrySet()) {
            final ButtomTabBean key = item.getKey();
            final ButtomItemDelegate value = item.getValue();
            ITEMS_BEANS.add(key);
            ITEM_DELEGATES.add(value);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            LayoutInflater.from(getContext()).inflate(R.layout.buttom_item_icon_text_layout,
                    buttom_bar);
            final RelativeLayout item = (RelativeLayout) buttom_bar.getChildAt(i);
            item.setTag(i);
            item.setOnClickListener(this);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView itemTextView = (AppCompatTextView) item.getChildAt(1);
            final ButtomTabBean bean = ITEMS_BEANS.get(i);
            itemIcon.setText(bean.getICON());
            itemTextView.setText(bean.getTITLE());
            if (i == mIndexDelegate) {
                itemIcon.setTextColor(mClickColor);
                itemTextView.setTextColor(mClickColor);
            }
        }

        final SupportFragment[] delegateArray = ITEM_DELEGATES.toArray(new SupportFragment[size]);
        loadMultipleRootFragment(R.id.delegate_container, mIndexDelegate, delegateArray);


    }

    @Override
    public void onClick(View v) {
        final int tag = (int) v.getTag();
        resetColor();
        final RelativeLayout item = (RelativeLayout) v;
        final IconTextView icon = (IconTextView) item.getChildAt(0);
        icon.setTextColor(Color.RED);
        final AppCompatTextView textView = (AppCompatTextView) item.getChildAt(1);
        textView.setTextColor(Color.RED);
        showHideFragment(ITEM_DELEGATES.get(tag), ITEM_DELEGATES.get(mCurrentDelegate));
        mCurrentDelegate = tag;
    }

    private void resetColor() {
        final int count = buttom_bar.getChildCount();
        for (int i = 0; i < count; i++) {
            final RelativeLayout item = (RelativeLayout) buttom_bar.getChildAt(i);
            final IconTextView icon = (IconTextView) item.getChildAt(0);
            icon.setTextColor(Color.GRAY);
            final AppCompatTextView textView = (AppCompatTextView) item.getChildAt(1);
            textView.setTextColor(Color.GRAY);
        }
    }

}
