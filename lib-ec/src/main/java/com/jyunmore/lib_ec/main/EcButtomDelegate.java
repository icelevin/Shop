package com.jyunmore.lib_ec.main;

import android.graphics.Color;

import com.jyunmore.lib_core.buttom.BaseButtomDelegate;
import com.jyunmore.lib_core.buttom.ButtomItemDelegate;
import com.jyunmore.lib_core.buttom.ButtomTabBean;
import com.jyunmore.lib_core.buttom.ItemBuilder;
import com.jyunmore.lib_ec.main.index.IndexDelegate;
import com.jyunmore.lib_ec.main.sort.SortDelegate;

import java.util.LinkedHashMap;

public class EcButtomDelegate extends BaseButtomDelegate {

    @Override
    public LinkedHashMap<ButtomTabBean, ButtomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<ButtomTabBean, ButtomItemDelegate> item = new LinkedHashMap<>();
        item.put(new ButtomTabBean("{fa-home}", "主页"), new IndexDelegate());
        item.put(new ButtomTabBean("{fa-sort}", "分类"), new SortDelegate());
        item.put(new ButtomTabBean("{fa-compass}", "发现"), new IndexDelegate());
        item.put(new ButtomTabBean("{fa-shopping-cart}", "购物车"), new IndexDelegate());
        item.put(new ButtomTabBean("{fa-user}", "我的"), new IndexDelegate());
        return builder.addItem(item).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickColor() {
        return Color.parseColor("#ffff8800");
    }
}
