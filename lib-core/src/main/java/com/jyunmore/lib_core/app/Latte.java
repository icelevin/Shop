package com.jyunmore.lib_core.app;


import android.content.Context;

import java.util.HashMap;

public final class Latte {
    public static Configurator init(Context context) {
        Configurator.getInstance()
                .getLatteConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return getConfigurator();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }


    public static <T> T getConfigurations(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Context getApplicationContext() {
        return (Context) getConfigurations(ConfigKeys.APPLICATION_CONTEXT);
    }
}
