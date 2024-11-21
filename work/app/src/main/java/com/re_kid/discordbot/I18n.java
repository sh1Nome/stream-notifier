package com.re_kid.discordbot;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.re_kid.discordbot.mapper.SystemSettingMapper;
import com.re_kid.discordbot.mapper.entity.SystemSetting;

/**
 * 多言語対応
 */
public class I18n {
    private final String baseName = "i18n/messages";
    private final SqlSessionFactory sqlSessionFactory;

    public I18n(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
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
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SystemSetting selectData = sqlSession.getMapper(SystemSettingMapper.class).selectById(guildId);
            if (selectData == null) {
                return getStringForNoSetting(key);
            }
            String langSettingValue = selectData.getLang();
            if ("en".equals(langSettingValue)) {
                return this.getStringForEn(key);
            } else if ("ja".equals(langSettingValue)) {
                return ResourceBundle.getBundle(baseName, Locale.JAPANESE).getString(key);
            } else {
                return getStringForNoSetting(key);
            }

        }
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
