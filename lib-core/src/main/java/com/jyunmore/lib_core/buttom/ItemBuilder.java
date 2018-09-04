package com.jyunmore.lib_core.buttom;

import java.util.LinkedHashMap;

public class ItemBuilder {
    private LinkedHashMap<ButtomTabBean, ButtomItemDelegate> ITEMS = new LinkedHashMap<>();

    static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public final ItemBuilder addItem(ButtomTabBean bean, ButtomItemDelegate delegate) {
        ITEMS.put(bean, delegate);
        return this;
    }

    public final ItemBuilder addItem(LinkedHashMap<ButtomTabBean, ButtomItemDelegate> map) {
        ITEMS.putAll(map);
        return this;
    }

    public final LinkedHashMap<ButtomTabBean, ButtomItemDelegate> build() {
        return ITEMS;
    }
}
