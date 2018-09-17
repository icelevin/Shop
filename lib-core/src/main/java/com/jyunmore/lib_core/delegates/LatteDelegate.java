package com.jyunmore.lib_core.delegates;

public abstract class LatteDelegate extends PermissionCheckerDelegate {
    public <T extends LatteDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }
}
