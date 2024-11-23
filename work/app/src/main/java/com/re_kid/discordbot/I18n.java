package com.re_kid.discordbot;

import java.util.Locale;
import java.util.ResourceBundle;

import com.re_kid.discordbot.db.entity.SystemSetting;
import com.re_kid.discordbot.db.repository.SystemSettingRepository;

/**
 * 多言語対応
 */
public class I18n {
    private final String baseName = "i18n/messages";
    private final SystemSettingRepository systemSettingRepository;

    public I18n(SystemSettingRepository systemSettingRepository) {
        this.systemSettingRepository = systemSettingRepository;
    }

    /**
     * キーに対応したメッセージを取得する
     * 
     * @param guildId ギルドID
     * @param key     キー
     * 
     * @return
     */
    public String getString(String guildId, String key) {
        SystemSetting selectData = systemSettingRepository.selectById(guildId);
        if (selectData == null) {
            return getStringForNoSetting(key);
        }
        String langSettingValue = selectData.getLang();
        if ("en".equals(langSettingValue)) {
            return this.getStringForEn(key);
        }
        if ("ja".equals(langSettingValue)) {
            return ResourceBundle.getBundle(baseName, Locale.JAPANESE).getString(key);
        }
        return getStringForNoSetting(key);

    }

    /**
     * キーに対応したメッセージ（英語）を取得する
     * 
     * @param key キー
     * @return
     */
    public String getStringForEn(String key) {
        return ResourceBundle.getBundle(baseName, Locale.ENGLISH).getString(key);
    }

    /**
     * キーに対応したメッセージを取得する
     * 
     * @param key キー
     * @return
     */
    private String getStringForNoSetting(String key) {
        return ResourceBundle.getBundle(baseName).getString(key);
    }
}
