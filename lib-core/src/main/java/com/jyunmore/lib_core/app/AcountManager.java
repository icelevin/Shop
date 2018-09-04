package com.jyunmore.lib_core.app;

import com.jyunmore.lib_core.utils.storage.LattePreference;

public class AcountManager {
    private enum SignTag {
        SIGN_TAG
    }

    public static void setSignState(boolean state) {
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    public static boolean isSignIn() {
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAcount(IUserChecker listener) {
        if (isSignIn()) {
            listener.onSignIn();
        } else {
            listener.onNotSignIn();
        }
    }
}
