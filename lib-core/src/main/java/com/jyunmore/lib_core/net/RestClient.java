package com.jyunmore.lib_core.net;

import android.content.Context;

import com.jyunmore.lib_core.net.callback.IError;
import com.jyunmore.lib_core.net.callback.IFailure;
import com.jyunmore.lib_core.net.callback.IRequest;
import com.jyunmore.lib_core.net.callback.ISuccess;
import com.jyunmore.lib_core.net.callback.RequestCallBacks;
import com.jyunmore.lib_core.net.download.DownloadHandler;
import com.jyunmore.lib_core.ui.LatteLoader;
import com.jyunmore.lib_core.ui.LoaderCreator;
import com.jyunmore.lib_core.ui.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RestClient {
    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreater.getParams();
    private final IRequest iRequest;
    private final ISuccess iSuccess;
    private final IFailure iFailure;
    private final IError iError;
    private final RequestBody requestBody;
    private final LoaderStyle loaderStyle;
    private final Context context;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final File file;


    public RestClient(String URL,
                      Map<String, Object> PARAMS,
                      IRequest iRequest,
                      ISuccess iSuccess,
                      IFailure iFailure,
                      IError iError,
                      RequestBody requestBody,
                      LoaderStyle loaderStyle,
                      Context context,
                      File file,
                      String downloadDir,
                      String extension,
                      String name

    ) {
        this.URL = URL;
        this.PARAMS.putAll(PARAMS);
        this.iRequest = iRequest;
        this.iSuccess = iSuccess;
        this.iFailure = iFailure;
        this.iError = iError;
        this.requestBody = requestBody;
        this.loaderStyle = loaderStyle;
        this.context = context;
        this.file = file;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
        final RestService service = RestCreater.getRestService();
        Call<String> call = null;
        if (iRequest != null) {
            iRequest.onRequestStart();
        }

        if (loaderStyle != null) {
            LatteLoader.showLoading(context, loaderStyle);
        }

        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;

            case POST_RAW:
                call = service.postRaw(URL, requestBody);
                break;

            case PUT_RAW:
                call = service.putRaw(URL, requestBody);
                break;

            case POST:
                call = service.post(URL, PARAMS);
                break;

            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case DOWNLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), file);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                call = RestCreater.getRestService().upload(URL, body);
                break;
            default:
                break;
        }
        if (call != null) {
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback() {
        return new RequestCallBacks(iRequest, iSuccess, iFailure, iError, loaderStyle);
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        if (requestBody == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("param must be null!");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put() {
        if (requestBody == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("param must be null!");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void download() {
        new DownloadHandler(URL, iRequest, iSuccess, iFailure, iError, DOWNLOAD_DIR, EXTENSION, NAME).handleDownload();
    }
}
