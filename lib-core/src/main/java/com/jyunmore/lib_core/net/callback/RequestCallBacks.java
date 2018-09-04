package com.jyunmore.lib_core.net.callback;

import com.jyunmore.lib_core.ui.LatteLoader;
import com.jyunmore.lib_core.ui.LoaderStyle;

import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestCallBacks implements Callback<String> {
    private final IRequest iRequest;
    private final ISuccess iSuccess;
    private final IFailure iFailure;
    private final IError iError;
    private final LoaderStyle loaderStyle;
    private static final android.os.Handler HANDLER = new android.os.Handler();

    public RequestCallBacks(IRequest iRequest, ISuccess iSuccess, IFailure iFailure, IError iError, LoaderStyle loaderStyle) {
        this.iRequest = iRequest;
        this.iSuccess = iSuccess;
        this.iFailure = iFailure;
        this.iError = iError;
        this.loaderStyle = loaderStyle;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                if (iSuccess != null) {
                    iSuccess.onSuccess(response.body());
                }
            }
        } else {
            if (iError != null) {
                iError.onError(response.code(), response.message());
            }
        }
        stopLoading();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (iFailure != null) {
            iFailure.onFailure();
        }
        if (iRequest != null) {
            iRequest.onRequestEnd();
        }
        stopLoading();
    }

    private void stopLoading() {
        if (loaderStyle != null) {
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatteLoader.stopLoading();
                }
            }, 1000);
        }
    }
}
