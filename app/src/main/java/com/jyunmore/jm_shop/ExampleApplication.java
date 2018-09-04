package com.jyunmore.jm_shop;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.jyunmore.lib_core.app.Latte;
import com.jyunmore.lib_core.net.interceptors.DebugInterceptor;
import com.jyunmore.lib_core.net.leancloud.BaseAvoCloud;
import com.jyunmore.lib_ec.datebase.DataBaseManager;
import com.jyunmore.lib_ec.icons.FontEcModel;

public class ExampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcons(new FontAwesomeModule())
                .withIcons(new FontEcModel())
                .withLeanCloud(new BaseAvoCloud(getApplicationContext(), "lMyH5YNuCheja4oBrj9UaTWE-gzGzoHsz",
                        "s0UNKA5uOTmcvpNXjiQaNxj7", true))
                .withIntecepter(new DebugInterceptor("index", R.raw.test))
                .withApiHost("http://www.baidu.com/")
//                .withIntecepter()
                .withAppId("")
                .withAppSecert("")
                .Configure();
//        initStetho();
        DataBaseManager.getInstance().init(this);
    }

    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }

}
