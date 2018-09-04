package com.jyunmore.lib_core.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jyunmore.lib_core.net.RestClient;
import com.jyunmore.lib_core.net.callback.ISuccess;
import com.jyunmore.lib_core.recycler.DataConventer;
import com.jyunmore.lib_core.recycler.MultipleRecyclerAdapter;


public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private final SwipeRefreshLayout refreshLayout;
    private final PagingBean BEAN;
    private final RecyclerView recyclerView;
    private MultipleRecyclerAdapter adapter = null;
    private final DataConventer dataConventer;


    private RefreshHandler(SwipeRefreshLayout refreshLayout, PagingBean BEAN,
                           RecyclerView recyclerView, DataConventer dataConventer) {
        this.refreshLayout = refreshLayout;
        this.BEAN = BEAN;
        this.recyclerView = recyclerView;
        this.dataConventer = dataConventer;
        refreshLayout.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout refreshLayout,
                                        RecyclerView recyclerView, DataConventer dataConventer) {
        return new RefreshHandler(refreshLayout, new PagingBean(), recyclerView, dataConventer);
    }

    private void reFresh() {
        refreshLayout.setRefreshing(true);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    public void firstPage(String url) {
        BEAN.setDelayed(1000);
        RestClient.builder()
                .url(url)
                .params("apikey", "G9skx01NNWjVDMNxo19m4DLWPds8H33ZRw8goBDXKjajJIcbYPxCEEO9Pp4FQ0CD")
                .params("kw", "康")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject jsonObject = JSON.parseObject(response);
                        Log.e("RESPONSE", jsonObject.toJSONString());
                        BEAN.setTotal(jsonObject.getJSONArray("data").size())
                                .setPageSize(15);
                        adapter = MultipleRecyclerAdapter.create(dataConventer.setJsonData(response));
                        adapter.setOnLoadMoreListener(RefreshHandler.this, recyclerView);
                        recyclerView.setAdapter(adapter);
                        BEAN.addIndex();
                    }
                })
                .builder()
                .get();
    }

    @Override
    public void onRefresh() {
        reFresh();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
