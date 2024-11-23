package com.re_kid.discordbot.command.notifyme;

import org.slf4j.Logger;

import com.re_kid.discordbot.I18n;
import com.re_kid.discordbot.command.Command;
import com.re_kid.discordbot.command.Prefix;
import com.re_kid.discordbot.db.entity.SystemSetting;
import com.re_kid.discordbot.db.repository.SystemSettingRepository;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class NotifyMe extends Command {

    public NotifyMe(Prefix prefix, String value, String optionSeparator, I18n i18n,
            SystemSettingRepository systemSettingRepository,
            Logger logger) {
        super(prefix, value, optionSeparator, i18n, systemSettingRepository, logger);
    }

    public void invoke(MessageReceivedEvent event) {
        super.invoke(event, e -> {
            String guildId = event.getGuild().getId();
            String channelId = event.getChannel().getId();
            SystemSetting systemSetting = systemSettingRepository.selectById(guildId);
            boolean changed = false;
            if (systemSetting == null) {
                SystemSetting insertedSetting = new SystemSetting(guildId, "", channelId);
                changed = systemSettingRepository.insertSystemSetting(insertedSetting);
            } else {
                SystemSetting updatedSetting = new SystemSetting(guildId, systemSetting.getLang(),
                        channelId);
                changed = systemSettingRepository.updateSystemSetting(updatedSetting);
            }
            String descriptionKey = changed ? "notifyme.succeeded" : "notifyme.failed";
            MessageEmbed messageEmbed = new EmbedBuilder().setDescription(this.i18n.getString(guildId, descriptionKey))
                    .build();
            MessageCreateData messageCreateData = new MessageCreateBuilder().setEmbeds(messageEmbed).build();
            e.getChannel().sendMessage(messageCreateData).queue(success -> this.recordLogSuccessful(),
                    error -> this.recordLogFailed());
        });
    }

}
