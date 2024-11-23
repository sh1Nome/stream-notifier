package com.re_kid.discordbot.command.help;

import java.util.List;

import org.slf4j.Logger;

import com.re_kid.discordbot.I18n;
import com.re_kid.discordbot.command.Command;
import com.re_kid.discordbot.command.Prefix;
import com.re_kid.discordbot.db.repository.SystemSettingRepository;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

/**
 * helpコマンド
 */
public class Help extends Command {

        public Help(Prefix prefix, String value, String optionSeparator, I18n i18n,
                        SystemSettingRepository systemSettingRepository,
                        Logger logger) {
                super(prefix, value, optionSeparator, i18n, systemSettingRepository, logger);
        }

        /**
         * helpコマンドを実行する
         * 
         * @param event メッセージ受信イベント
         */
        public void invoke(MessageReceivedEvent event) {
                super.invoke(event, e -> {
                        String guildId = e.getGuild().getId();
                        OptionRowMessageData optionForYoutube = new OptionRowMessageData(
                                        guildId,
                                        this.i18n,
                                        "help.command.option.youtube",
                                        "help.command.option.youtube.description");
                        OptionRowMessageData optionForTwitch = new OptionRowMessageData(
                                        guildId,
                                        this.i18n,
                                        "help.command.option.twitch",
                                        "help.command.option.twitch.description");
                        List<OptionRowMessageData> optionsForRegistAndUnregist = List.of(optionForYoutube,
                                        optionForTwitch);

                        Field commandForRegist = new Field(
                                        this.getCustomizedFieldNameForCommand(guildId, "help.command.regist"),
                                        this.getCustomizedFieldValueForCommand(guildId,
                                                        "help.command.regist.description", optionsForRegistAndUnregist),
                                        false);
                        Field commandForUnregist = new Field(
                                        this.getCustomizedFieldNameForCommand(guildId, "help.command.unregist"),
                                        this.getCustomizedFieldValueForCommand(guildId,
                                                        "help.command.unregist.description",
                                                        optionsForRegistAndUnregist),
                                        false);

                        OptionRowMessageData optionForEn = new OptionRowMessageData(guildId, this.i18n,
                                        "help.command.lang.option.en", "help.command.lang.option.en.description");
                        OptionRowMessageData optionForJa = new OptionRowMessageData(guildId, this.i18n,
                                        "help.command.lang.option.ja", "help.command.lang.option.ja.description");
                        List<OptionRowMessageData> optionsForLang = List.of(optionForEn, optionForJa);
                        Field commandForLang = new Field(
                                        this.getCustomizedFieldNameForCommand(guildId, "help.command.lang"),
                                        this.getCustomizedFieldValueForCommand(guildId, "help.command.lang.description",
                                                        optionsForLang),
                                        false);

                        MessageEmbed embed = new EmbedBuilder()
                                        .setDescription(this.i18n.getString(guildId,
                                                        "help.description"))
                                        .addField(commandForRegist)
                                        .addField(commandForUnregist)
                                        .addField(commandForLang)
                                        .build();

                        MessageCreateData data = new MessageCreateBuilder()
                                        .setEmbeds(embed)
                                        .setTTS(false).build();

                        e.getChannel().sendMessage(data)
                                        .queue(success -> this.recordLogSuccessful(),
                                                        error -> this.recordLogFailed());
                });
        }

        /**
         * コマンド用にカスタマイズされたEmbedのフィールドネームを取得する
         * 
         * @param guildId ギルドID
         * @param key     キー
         * 
         * @return コマンド用にカスタマイズされたEmbedのフィールドネーム
         */
        private String getCustomizedFieldNameForCommand(String guildId, String key) {
                return this.prefix.toString() + this.i18n.getString(guildId, key);
        }

        /**
         * コマンド用にカスタマイズされたEmbedのフィールドバリューを取得する
         * 
         * @param guildId               ギルドID
         * @param descriptionKey        説明キー
         * @param optionRowMessageDatas コマンド用にカスタマイズされたEmbedのフィールドバリューのオプション行（複数）
         * 
         * @return コマンド用にカスタマイズされたEmbedのフィールドバリュー
         */
        private String getCustomizedFieldValueForCommand(String guildId,
                        String descriptionKey, List<OptionRowMessageData> optionRowMessageDatas) {
                String fieldValue = """
                                %s: %s
                                %s:
                                """.formatted(this.i18n.getString(guildId, "help.label.description"),
                                this.i18n.getString(guildId, descriptionKey),
                                this.i18n.getString(guildId, "help.label.option"));
                for (OptionRowMessageData optionRowMessageData : optionRowMessageDatas) {
                        fieldValue += optionRowMessageData.toString(this.optionSeparator);
                }
                return fieldValue;
        }

}
