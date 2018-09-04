package com.jyunmore.lib_core.net.download;


import android.os.AsyncTask;

import com.jyunmore.lib_core.net.RestCreater;
import com.jyunmore.lib_core.net.callback.IError;
import com.jyunmore.lib_core.net.callback.IFailure;
import com.jyunmore.lib_core.net.callback.IRequest;
import com.jyunmore.lib_core.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadHandler {

    public static final WeakHashMap<String, Object> PARAMS = RestCreater.ParamHolder.PARAMS;
    private String mURL;
    private IRequest mRequest;
    private ISuccess mSuccess;
    private IFailure mFailure;
    private IError mError;
    private String mDownloadDir;
    private String mExtension;
    private String mName;

    public DownloadHandler(String mURL, IRequest mRequest, ISuccess mSuccess, IFailure mFailure, IError mError, String mDownloadDir, String mExtension, String mName) {
        this.mURL = mURL;
        this.mRequest = mRequest;
        this.mSuccess = mSuccess;
        this.mFailure = mFailure;
        this.mError = mError;
        this.mDownloadDir = mDownloadDir;
        this.mExtension = mExtension;
        this.mName = mName;
    }

    public final void handleDownload() {
        if (mRequest != null) {
            mRequest.onRequestStart();
        }
        RestCreater.getRestService().download(mURL, PARAMS).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    final ResponseBody body = response.body();
                    final SaveFileTask saveFileTask = new SaveFileTask(mRequest, mSuccess);
                    saveFileTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mDownloadDir, mExtension);
                    if (saveFileTask.isCancelled()) {
                        if (mRequest != null) {
                            mRequest.onRequestEnd();
                        }
                    }
                }else {
                    if (mError != null) {
                        mError.onError(response.code(),response.message());
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (mFailure != null) {
                    mFailure.onFailure();
                }
            }
        });

    }

}
