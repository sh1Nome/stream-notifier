package com.re_kid.discordbot.mapper;

import com.re_kid.discordbot.mapper.entity.SystemSetting;

public interface SystemSettingMapper {

    SystemSetting selectById(String id);

    boolean updateSystemSetting(SystemSetting systemSetting);
}
