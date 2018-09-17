package com.jyunmore.lib_ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.joanzapata.iconify.widget.IconTextView;
import com.jyunmore.lib_core.buttom.ButtomItemDelegate;
import com.jyunmore.lib_core.net.RestClient;
import com.jyunmore.lib_core.net.callback.ISuccess;
import com.jyunmore.lib_core.recycler.BaseDecoration;
import com.jyunmore.lib_core.recycler.MultipleFields;
import com.jyunmore.lib_core.recycler.MultipleItemEntity;
import com.jyunmore.lib_core.refresh.RefreshHandler;
import com.jyunmore.lib_ec.R;
import com.jyunmore.lib_ec.R2;
import com.jyunmore.lib_ec.main.EcButtomDelegate;

import java.util.ArrayList;

import butterknife.BindView;

public class IndexDelegate extends ButtomItemDelegate {

    @BindView(R2.id.swpie_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R2.id.rl_view)
    RecyclerView mRecyclerView;
    @BindView(R2.id.tb_index)
    Toolbar mToolBar;
    @BindView(R2.id.itv_scan)
    IconTextView mScan;
    @BindView(R2.id.et_search)
    AppCompatEditText mSearch;
    @BindView(R2.id.itv_bullhorn)
    IconTextView mBullhorn;


    private RefreshHandler refreshHandler;


    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        refreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new IndexDataConverter());
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        refreshHandler.firstPage("http://120.76.205.241:8000/product/xiubaobao");
    }

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mRefreshLayout.setProgressViewOffset(true, 120, 300);
    }

    private void initRecyclerView() {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addItemDecoration(BaseDecoration.create(
                ContextCompat.getColor(getContext(), R.color.app_background), 5));
        final EcButtomDelegate ecButtomDelegate = getParentDelegate();
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(ecButtomDelegate));
    }


}
