package com.re_kid.discordbot;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.re_kid.discordbot.mapper.SystemSettingMapper;

public class I18n {
    private final String baseName = "i18n/messages";
    private final SqlSessionFactory sqlSessionFactory;

    public I18n(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public String getString(String key) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            String langSettingValue = sqlSession.getMapper(SystemSettingMapper.class).selectById("lang")
                    .getValue();
            if ("en".equals(langSettingValue)) {
                return ResourceBundle.getBundle(baseName, Locale.ENGLISH).getString(key);
            } else if ("ja".equals(langSettingValue)) {
                return ResourceBundle.getBundle(baseName, Locale.JAPANESE).getString(key);
            } else {
                return ResourceBundle.getBundle(baseName).getString(key);
            }

        }
    }
}
