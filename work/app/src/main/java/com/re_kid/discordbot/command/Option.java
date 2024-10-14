package com.re_kid.discordbot.command;

import com.google.common.base.Strings;

/**
 * コマンドオプションの親クラス
 */
public class Option {

    private final String separator = "--";
    private final String value;

    public Option(String value) {
        this.value = Strings.nullToEmpty(value);
    }

    /**
     * オプションが等しいか確認する
     * 
     * @param option 確認するオプション
     * @return 等しいならtrue
     */
    public boolean equals(Option option) {
        return this.getValue().equals(option.getValue()) ? true : false;
    }

    public String getSeparator() {
        return separator;
    }

    private String getValue() {
        return value;
    }

}
