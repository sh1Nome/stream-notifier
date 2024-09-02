package com.re_kid.discordbot.command;

/**
 * コマンドの接頭辞
 */
public class Prefix {

    private final String value;

    private final String separator;

    public Prefix(String value, String separator) {
        this.value = value;
        this.separator = separator;
    }

    @Override
    public String toString() {
        return this.value + this.separator;
    }

    /**
     * 接頭辞のセパレーターを取得する
     * 
     * @return 接頭辞のセパレーター
     */
    public String getSeparator() {
        return this.separator;
    }

}
