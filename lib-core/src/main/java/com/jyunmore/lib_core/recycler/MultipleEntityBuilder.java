package com.jyunmore.lib_core.recycler;

import android.support.annotation.NonNull;

import java.util.LinkedHashMap;
import java.util.WeakHashMap;

public class MultipleEntityBuilder {
    private static final LinkedHashMap<Object, Object> FIELDS = new LinkedHashMap<>();

    private MultipleEntityBuilder() {
        FIELDS.clear();
    }

    public final MultipleEntityBuilder setItemType(int itemType) {
        FIELDS.put(MultipleFields.ITEM_TYPE, itemType);
        return this;
    }
    @NonNull
    public static MultipleEntityBuilder builder() {
        return new MultipleEntityBuilder();
    }
    public final MultipleEntityBuilder setField(Object key, Object value) {
        FIELDS.put(key, value);
        return this;
    }

    @NonNull
    public final MultipleItemEntity build() {
        return new MultipleItemEntity(FIELDS);
    }
}
