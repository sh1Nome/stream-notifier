package com.re_kid.discordbot.mapper;

import com.re_kid.discordbot.mapper.entity.SystemSetting;

public interface SystemSettingMapper {

    SystemSetting selectById(String guildId);

    boolean insertSystemSetting(SystemSetting systemSetting);

    boolean updateSystemSetting(SystemSetting systemSetting);
}
