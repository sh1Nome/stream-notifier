package com.re_kid.discordbot.db.repository;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.re_kid.discordbot.db.entity.SystemSetting;
import com.re_kid.discordbot.db.mapper.SystemSettingMapper;

public class SystemSettingRepository {

    private final SqlSessionFactory sqlSessionFactory;

    public SystemSettingRepository(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public SystemSetting selectById(String guildId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SystemSettingMapper systemSettingMapper = sqlSession.getMapper(SystemSettingMapper.class);
            SystemSetting selectData = systemSettingMapper.selectById(guildId);
            return selectData;
        }
    }

    public boolean insertSystemSetting(SystemSetting systemSetting) {
        boolean success = false;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SystemSettingMapper systemSettingMapper = sqlSession.getMapper(SystemSettingMapper.class);
            success = systemSettingMapper.insertSystemSetting(systemSetting);
            sqlSession.commit();
        }
        return success;
    }

    public boolean updateSystemSetting(SystemSetting systemSetting) {
        boolean success = false;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SystemSettingMapper systemSettingMapper = sqlSession.getMapper(SystemSettingMapper.class);
            success = systemSettingMapper.updateSystemSetting(systemSetting);
            sqlSession.commit();
        }
        return success;
    }

}
