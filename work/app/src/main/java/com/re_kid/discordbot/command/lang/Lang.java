package com.re_kid.discordbot.command.lang;

import java.util.Arrays;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import com.re_kid.discordbot.I18n;
import com.re_kid.discordbot.command.Command;
import com.re_kid.discordbot.command.CommandStatus;
import com.re_kid.discordbot.command.Option;
import com.re_kid.discordbot.command.Prefix;
import com.re_kid.discordbot.command.lang.option.En;
import com.re_kid.discordbot.command.lang.option.Ja;
import com.re_kid.discordbot.mapper.SystemSettingMapper;
import com.re_kid.discordbot.mapper.entity.SystemSetting;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;

public class Lang extends Command {

    private final En en;
    private final Ja ja;

    public Lang(Prefix prefix, String value, CommandStatus commandStatus, String optionSeparator, En en, Ja ja,
            SqlSessionFactory sqlSessionFactory, I18n i18n, Logger logger) {
        super(prefix, value, commandStatus, optionSeparator, i18n, sqlSessionFactory, logger);
        this.en = en;
        this.ja = ja;
    }

    public void invoke(MessageReceivedEvent event) {
        super.invoke(event, e -> {
            Arrays.asList(event.getMessage().getContentRaw().split(" ")).stream()
                    .filter(splitMessage -> splitMessage.startsWith(this.optionSeparator))
                    .map(optionMessage -> new Option(optionMessage.replace(this.optionSeparator, "")))
                    .filter(generatedOptionFromMessage -> {
                        return en.equals(generatedOptionFromMessage)
                                || ja.equals(generatedOptionFromMessage);
                    })
                    .findFirst()
                    .ifPresentOrElse(
                            scheduledOption -> this.executeOption(scheduledOption, e).queue(
                                    success -> {
                                        this.changeStatusToNoFailedAndRecordLogResult();
                                    },
                                    error -> {
                                        this.changeStatusToFailedAndRecordLogResult();
                                    }),
                            () -> {
                                this.changeStatusToFailedAndRecordLogResult();
                            });
        });
    }

    private MessageCreateAction executeOption(Option scheduledOption, MessageReceivedEvent event) {
        if (en.equals(scheduledOption)) {
            return executeEnOption(event);
        } else {
            return executeJaOption(event);
        }
    }

    private MessageCreateAction executeEnOption(MessageReceivedEvent event) {
        boolean success = false;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SystemSettingMapper systemSettingMapper = sqlSession.getMapper(SystemSettingMapper.class);
            success = systemSettingMapper.updateSystemSetting(new SystemSetting(this.value, en.getValue()));
            sqlSession.commit();
        }
        return this.sendMessage(event, success);
    }

    private MessageCreateAction executeJaOption(MessageReceivedEvent event) {
        boolean success = false;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SystemSettingMapper systemSettingMapper = sqlSession.getMapper(SystemSettingMapper.class);
            success = systemSettingMapper.updateSystemSetting(new SystemSetting(this.value, ja.getValue()));
            sqlSession.commit();
        }
        return this.sendMessage(event, success);
    }

    private MessageCreateAction sendMessage(MessageReceivedEvent event, boolean success) {
        return success ? event.getChannel().sendMessage(this.i18n.getString("lang.succeeded"))
                : event.getChannel().sendMessage(this.i18n.getString("lang.failed"));
    }

}
