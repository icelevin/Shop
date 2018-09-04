package com.jyunmore.lib_core.net;

import android.content.Context;

import com.jyunmore.lib_core.net.callback.IError;
import com.jyunmore.lib_core.net.callback.IFailure;
import com.jyunmore.lib_core.net.callback.IRequest;
import com.jyunmore.lib_core.net.callback.ISuccess;
import com.jyunmore.lib_core.ui.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RestClientBuilder {
    public static final Map<String, Object> PARAMS = RestCreater.ParamHolder.PARAMS;
    private String mURL;
    private IRequest mRequest;
    private ISuccess mSuccess;
    private IFailure mFailure;
    private IError mError;
    private RequestBody mBody;
    private Context mContext;
    private LoaderStyle mLoaderStyle;
    private File mFile;
    private String mDownloadDir;
    private String mExtension = null;
    private String mName = null;


    RestClientBuilder() {

    }

    public final RestClientBuilder url(String url) {
        this.mURL = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> param) {
        PARAMS.putAll(param);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        this.PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder file(String file) {
        this.mFile = new File(file);
        return this;
    }

    public final RestClientBuilder name(String name) {
        this.mName = name;
        return this;
    }

    public final RestClientBuilder dir(String downloadDir) {
        this.mDownloadDir = downloadDir;
        return this;
    }

    public final RestClientBuilder extension(String extension) {
        this.mExtension = extension;
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess) {
        this.mSuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        this.mFailure = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError) {
        this.mError = iError;
        return this;
    }

    public final RestClientBuilder request(IRequest iRequest) {
        this.mRequest = iRequest;
        return this;
    }

    private Map<String, Object> checkParams() {
        if (PARAMS == null) {
            return new WeakHashMap<>();
        }
        return PARAMS;
    }

    public final RestClient builder() {
        return new RestClient(
                mURL,
                PARAMS,
                mRequest,
                mSuccess,
                mFailure,
                mError,
                mBody,
                mLoaderStyle,
                mContext,
                mFile,
                mDownloadDir,
                mExtension,
                mName);
    }

    public final RestClientBuilder loader(Context context, LoaderStyle loaderStyle) {
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        return this;
    }

    public final RestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }
}
