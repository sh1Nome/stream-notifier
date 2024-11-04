package com.re_kid.discordbot.command.lang;

import java.util.Arrays;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import com.re_kid.discordbot.I18n;
import com.re_kid.discordbot.command.Command;
import com.re_kid.discordbot.command.Option;
import com.re_kid.discordbot.command.Prefix;
import com.re_kid.discordbot.command.lang.option.En;
import com.re_kid.discordbot.command.lang.option.Ja;
import com.re_kid.discordbot.mapper.SystemSettingMapper;
import com.re_kid.discordbot.mapper.entity.SystemSetting;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

/**
 * langコマンド
 */
public class Lang extends Command {

    private final En en;
    private final Ja ja;
    private final JDA jda;

    public Lang(Prefix prefix, String value, String optionSeparator, En en, Ja ja, JDA jda,
            SqlSessionFactory sqlSessionFactory, I18n i18n, Logger logger) {
        super(prefix, value, optionSeparator, i18n, sqlSessionFactory, logger);
        this.en = en;
        this.ja = ja;
        this.jda = jda;
    }

    /**
     * langコマンドを実行する
     * 
     * @param event メッセージ受信イベント
     */
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
                                        this.recordLogSuccessfulCommand();
                                    },
                                    error -> {
                                        this.recordLogFailedCommand();
                                    }),
                            () -> {
                                this.recordLogFailedCommand();
                            });
        });
    }

    /**
     * オプションを実行する
     * 
     * @param scheduledOption 実行予定のオプション
     * @param event           メッセージ受信イベント
     * @return
     */
    private MessageCreateAction executeOption(Option scheduledOption, MessageReceivedEvent event) {
        if (en.equals(scheduledOption)) {
            return executeEnOption(event);
        } else {
            return executeJaOption(event);
        }
    }

    /**
     * enオプションを実行する
     * 
     * @param event メッセージ受信イベント
     * @return
     */
    private MessageCreateAction executeEnOption(MessageReceivedEvent event) {
        boolean success = false;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SystemSettingMapper systemSettingMapper = sqlSession.getMapper(SystemSettingMapper.class);
            success = systemSettingMapper.updateSystemSetting(new SystemSetting(this.value, en.getValue()));
            sqlSession.commit();
        }
        this.jda.getPresence().setActivity(Activity.of(ActivityType.CUSTOM_STATUS, "English"));
        return this.sendMessage(event, success);
    }

    /**
     * jaオプションを実行する
     * 
     * @param event メッセージ受信イベント
     * @return
     */
    private MessageCreateAction executeJaOption(MessageReceivedEvent event) {
        boolean success = false;
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            SystemSettingMapper systemSettingMapper = sqlSession.getMapper(SystemSettingMapper.class);
            success = systemSettingMapper.updateSystemSetting(new SystemSetting(this.value, ja.getValue()));
            sqlSession.commit();
        }
        this.jda.getPresence().setActivity(Activity.of(ActivityType.CUSTOM_STATUS, "日本語"));
        return this.sendMessage(event, success);
    }

    /**
     * Discordにメッセージを送信する
     * 
     * @param event   メッセージ受信イベント
     * @param success 成功フラグ
     * @return
     */
    private MessageCreateAction sendMessage(MessageReceivedEvent event, boolean success) {
        return success
                ? event.getChannel()
                        .sendMessage(this.getMessageCreateDataWithDescription(
                                "lang.succeeded"))
                : event.getChannel().sendMessage(this.getMessageCreateDataWithDescription(
                        "lang.failed"));
    }

    /**
     * 説明付きのメッセージ送信データを取得する
     * 
     * @param description 説明
     * @return 説明付きのメッセージ送信データ
     */
    private MessageCreateData getMessageCreateDataWithDescription(String description) {
        return new MessageCreateBuilder()
                .setEmbeds(new EmbedBuilder()
                        .setDescription(this.i18n.getString(description)).build())
                .setTTS(false).build();
    }

}
