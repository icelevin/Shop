package com.jyunmore.lib_core.app;

import android.app.Activity;

import com.avos.avoscloud.AVOSCloud;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.jyunmore.lib_core.net.leancloud.BaseAvoCloud;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

public class Configurator {
    private static final HashMap<Object, Object> LATTE_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    public Configurator() {
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READYED, false);
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    public final Configurator withIcons(IconFontDescriptor initializer) {
        ICONS.add(initializer);
        return this;
    }

    final HashMap<Object, Object> getLatteConfigs() {
        return LATTE_CONFIGS;
    }

    private static class Holder {
        private final static Configurator INSTANCE = new Configurator();

    }

    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withLeanCloud(BaseAvoCloud avosCloud) {
        LATTE_CONFIGS.put(ConfigKeys.LEANCLOUD, avosCloud);
        return this;
    }

    public final Configurator withIntecepter(Interceptor intecepter) {
        INTERCEPTORS.add(intecepter);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withIntecepters(ArrayList<Interceptor> intecepters) {
        INTERCEPTORS.addAll(intecepters);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final void Configure() {
        initIcons();
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READYED, true);
    }

    public final Configurator withApiHost(String host) {
        LATTE_CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigKeys.CONFIG_READYED);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = LATTE_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + "IS NULL");
        }
        return (T) value;
    }

    public final Configurator withAppId(String appId) {
        LATTE_CONFIGS.put(ConfigKeys.WE_CHAT_APP_ID, appId);
        return this;
    }

    public final Configurator withAppSecert(String secert) {
        LATTE_CONFIGS.put(ConfigKeys.WE_CHAT_APP_SECRET, secert);
        return this;
    }

    public final Configurator withActivity(Activity activity) {
        LATTE_CONFIGS.put(ConfigKeys.ACTIVITY, activity);
        return this;
    }
}
