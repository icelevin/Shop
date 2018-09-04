package com.jyunmore.lib_ec.main.index;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jyunmore.lib_core.recycler.DataConventer;
import com.jyunmore.lib_core.recycler.ItemType;
import com.jyunmore.lib_core.recycler.MultipleEntityBuilder;
import com.jyunmore.lib_core.recycler.MultipleFields;
import com.jyunmore.lib_core.recycler.MultipleItemEntity;

import java.util.ArrayList;

public class IndexDataConverter extends DataConventer {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final String title = data.getString("title");
            final String subTitle = data.getString("subtitle");
            final int spanSize = 2;
            final String id = data.getString("id");
            final JSONArray banners = data.getJSONArray("imageUrls");
            final ArrayList<String> bannerImages = new ArrayList<>();

            int type = 0;
            if (banners == null && title != null) {
                type = ItemType.TEXT;
            } else if (banners != null && title == null) {
                type = ItemType.IMAGE;
            } else if (banners != null && title != null) {
                type = ItemType.IMAGE_TEXT;
            } else if (banners != null) {
                type = ItemType.BANNER;
                int count = banners.size();
                for (int j = 0; j < count; j++) {
                    final String banner = banners.getString(j);
                    bannerImages.add(banner);
                }
            }
            String imgUrl = "";
            if (banners != null && banners.size() > 0) {
                imgUrl = banners.get(0).toString();
            }

            final MultipleItemEntity entity = MultipleEntityBuilder.builder()
                    .setField(MultipleFields.ITEM_TYPE, type)
                    .setField(MultipleFields.SPACE_SIZE, spanSize)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.TEXT, title)
                    .setField(MultipleFields.BANNER, bannerImages)
                    .setField(MultipleFields.IMAGE_URL, imgUrl)
                    .build();
            ENTITYS.add(entity);
        }
        return ENTITYS;
    }


}
