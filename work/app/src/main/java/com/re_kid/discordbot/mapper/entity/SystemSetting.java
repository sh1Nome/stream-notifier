package com.re_kid.discordbot.mapper.entity;

public class SystemSetting {
    private String guildId;
    private String lang;
    private String channelId;

    public SystemSetting(String guildId, String lang, String channelId) {
        this.guildId = guildId;
        this.lang = lang;
        this.channelId = channelId;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getLang() {
        return lang;
    }

    public String getChannelId() {
        return channelId;
    }

}
