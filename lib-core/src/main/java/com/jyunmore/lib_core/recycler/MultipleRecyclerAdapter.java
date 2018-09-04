package com.jyunmore.lib_core.recycler;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jyunmore.lib_core.R;
import com.jyunmore.lib_core.ui.banner.BannerCreator;

import java.util.ArrayList;
import java.util.List;

public class MultipleRecyclerAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity,
        MultipleViewHolder> implements BaseQuickAdapter.SpanSizeLookup, OnItemClickListener {

    public MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    //确保初始化一次banner,防止重复item加载
    private boolean mIsInitBanner = false;

    public static MultipleRecyclerAdapter create(List<MultipleItemEntity> data) {
        return new MultipleRecyclerAdapter(data);

    }

    public static MultipleRecyclerAdapter create(DataConventer conventer) {
        return new MultipleRecyclerAdapter(conventer.convert());
    }

    private void init() {
        addItemType(ItemType.TEXT, R.layout.item_multi_text);
        addItemType(ItemType.IMAGE, R.layout.item_multi_img);
        addItemType(ItemType.IMAGE_TEXT, R.layout.item_multi_img_text);
        addItemType(ItemType.BANNER, R.layout.item_multi_banner);
        setSpanSizeLookup(this);
        openLoadAnimation();
        isFirstOnly(false);

    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity item) {
        final String title;
        final String imageUrl;
        final ArrayList<String> bannerImage;
        switch (holder.getItemViewType()) {
            case ItemType.TEXT:
                title = item.getField(MultipleFields.TEXT);
                holder.setText(R.id.tv_text_style, title);
                break;
            case ItemType.IMAGE:
                imageUrl = item.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .dontAnimate()
                                .centerCrop())
                        .into((AppCompatImageView) holder.getView(R.id.iv_single));
                break;
            case ItemType.IMAGE_TEXT:
                title = item.getField(MultipleFields.TEXT);
                imageUrl = item.getField(MultipleFields.IMAGE_URL);
                holder.setText(R.id.tv_text_style, title);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(new RequestOptions()
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .dontAnimate())
                        .into((AppCompatImageView) holder.getView(R.id.iv_single));
                break;
            case ItemType.BANNER:
                if (!mIsInitBanner) {
                    bannerImage = item.getField(MultipleFields.BANNER);
                    final ConvenientBanner<String> convenientBanner = holder.getView(R.id.cb_banner);
                    BannerCreator.setDefault(convenientBanner, bannerImage, this);
                    mIsInitBanner = true;
                }
                break;
            default:
                break;
        }

    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPACE_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }
}
