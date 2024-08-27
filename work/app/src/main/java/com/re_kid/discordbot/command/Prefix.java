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

}
