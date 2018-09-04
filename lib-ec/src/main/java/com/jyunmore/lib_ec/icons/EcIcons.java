package com.jyunmore.lib_ec.icons;

import com.joanzapata.iconify.Icon;

public enum EcIcons implements Icon{
    jiqiren('\ue604'),
    qiadai('\ue603'),
    upload('\ue7f2')
    ;
    private char aChar;

    EcIcons(char aChar) {
        this.aChar = aChar;
    }

    @Override
    public String key() {
        return name().replace("_","-");
    }

    @Override
    public char character() {
        return aChar;
    }
}
