package com.re_kid.discordbot.db.mapper;

import com.re_kid.discordbot.db.entity.SystemSetting;

public interface SystemSettingMapper {

    SystemSetting selectById(String guildId);

    boolean insertSystemSetting(SystemSetting systemSetting);

    boolean updateSystemSetting(SystemSetting systemSetting);
}
