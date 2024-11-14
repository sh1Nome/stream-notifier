package com.re_kid.discordbot.command.help;

import com.re_kid.discordbot.I18n;

/**
 * コマンド用にカスタマイズされたEmbedのフィールドバリューのオプション行
 */
public class OptionRowMessageData {
    private final I18n i18n;
    private final String value;
    private final String description;

    public OptionRowMessageData(String guildId, I18n i18n, String valueKey,
            String descriptionKey) {
        this.i18n = i18n;
        this.value = this.i18n.getString(guildId, valueKey);
        this.description = this.i18n.getString(guildId, descriptionKey);
    }

    /**
     * コマンド用にカスタマイズされたEmbedのフィールドバリューのオプション行を1行を取得する
     * 
     * @param optionSeparator コマンドオプションのセパレーター
     * @return コマンド用にカスタマイズされたEmbedのフィールドバリューのオプション行
     */
    public String toString(String optionSeparator) {
        return """
                    `%s%s`: %s
                """.formatted(optionSeparator, value, description);
    }

}
