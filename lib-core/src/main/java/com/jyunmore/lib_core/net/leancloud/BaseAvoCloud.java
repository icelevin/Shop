package com.jyunmore.lib_core.net.leancloud;

import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

public class BaseAvoCloud {
    private Context context;
    private String appId;
    private String key;
    private boolean isDebug;

    public BaseAvoCloud(Context context, String appId, String key, boolean isDebug) {
        this.context = context;
        this.appId = appId;
        this.key = key;
        this.isDebug = isDebug;
        AVOSCloud.initialize(context, appId, key);
        AVOSCloud.setDebugLogEnabled(isDebug);

        // 测试 SDK 是否正常工作的代码
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words","Hello World!");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Log.d("saved","success!");
                }
            }
        });
    }

}
