package com.jyunmore.jm_shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.jyunmore.lib_core.delegates.LatteDelegate;
import com.jyunmore.lib_core.net.RestClient;
import com.jyunmore.lib_core.net.callback.IError;
import com.jyunmore.lib_core.net.callback.IFailure;
import com.jyunmore.lib_core.net.callback.ISuccess;


public class ExampleDelegate extends LatteDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        testRestCilent();
    }

    private void testRestCilent() {
        RestClient.builder()
                .url("http://127.0.0.1/index")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("gjgj", response);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .builder()
                .get();
    }
}
