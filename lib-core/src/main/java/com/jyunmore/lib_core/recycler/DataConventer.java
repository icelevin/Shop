package com.jyunmore.lib_core.recycler;

import java.util.ArrayList;

public abstract class DataConventer {
    protected final ArrayList<MultipleItemEntity> ENTITYS = new ArrayList<>();
    private String mJsonData = null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConventer setJsonData(String json) {
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData() {
        if (mJsonData == null || mJsonData.isEmpty()) {
            throw new NullPointerException("Data is Null!");
        }
        return mJsonData;
    }
}
