package com.re_kid.discordbot;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
    private final String baseName = "i18n/messages";

    public String getString(Locale locale, String key) {
        return ResourceBundle.getBundle(baseName, locale).getString(key);
    }

    public String getString(String key) {
        return ResourceBundle.getBundle(baseName).getString(key);
    }
}
