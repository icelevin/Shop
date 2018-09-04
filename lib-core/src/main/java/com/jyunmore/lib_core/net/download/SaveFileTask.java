package com.jyunmore.lib_core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.speech.tts.Voice;

import com.jyunmore.lib_core.app.Latte;
import com.jyunmore.lib_core.net.callback.IRequest;
import com.jyunmore.lib_core.net.callback.ISuccess;
import com.jyunmore.lib_core.utils.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

public class SaveFileTask extends AsyncTask<Object, Voice, File> {
    private final IRequest iRequest;
    private final ISuccess iSuccess;

    public SaveFileTask(IRequest iRequest, ISuccess iSuccess) {
        this.iRequest = iRequest;
        this.iSuccess = iSuccess;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        String name = (String) params[3];
        final InputStream is = body.byteStream();
        if (downloadDir == null || "".equals(downloadDir)) {
            downloadDir = "down_loads";

        }
        if (extension == null || "".equals(extension)) {
            extension = "";
        }
        if (name == null) {
            return FileUtil.writeToDisk(is, downloadDir, extension.toUpperCase(), extension);
        } else {
            return FileUtil.writeToDisk(is, downloadDir, name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (iSuccess != null) {
            iSuccess.onSuccess(file.getPath());
        }
        if (iRequest != null) {
            iRequest.onRequestEnd();
        }
        autoInstallApk(file);
    }

    private void autoInstallApk(File file) {
        if (FileUtil.getExtension(file.getPath()).contains("apk")){
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            Latte.getApplicationContext().startActivity(install);
        }
    }
}
