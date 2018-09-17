package com.jyunmore.lib_ec.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.jyunmore.lib_core.delegates.LatteDelegate;
import com.jyunmore.lib_ec.main.detail.GoodsDetailDelegate;

import retrofit2.Response;

public class IndexItemClickListener extends SimpleClickListener {
    private final LatteDelegate DELEGATE;

    private IndexItemClickListener(LatteDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    public static IndexItemClickListener create(LatteDelegate DELEGATE) {
        return new IndexItemClickListener(DELEGATE);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final GoodsDetailDelegate goodsDetailDelegate = GoodsDetailDelegate.create();
        DELEGATE.start(goodsDetailDelegate);
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
