package com.jyunmore.lib_core.buttom;

public final class ButtomTabBean {
    private final CharSequence ICON;
    private final CharSequence TITLE;

    public ButtomTabBean(CharSequence ICON, CharSequence TITLE) {
        this.ICON = ICON;
        this.TITLE = TITLE;
    }

    public CharSequence getICON() {
        return ICON;
    }

    public CharSequence getTITLE() {
        return TITLE;
    }
}
