package com.re_kid.discordbot.command.help;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import com.re_kid.discordbot.I18n;
import com.re_kid.discordbot.command.Command;
import com.re_kid.discordbot.command.CommandStatus;
import com.re_kid.discordbot.command.Prefix;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

/**
 * helpコマンド
 */
public class Help extends Command {

    public Help(Prefix prefix, String value, CommandStatus commandStatus, String optionSeparator, I18n i18n,
            SqlSessionFactory sqlSessionFactory, Logger logger) {
        super(prefix, value, commandStatus, optionSeparator, i18n, sqlSessionFactory, logger);
    }

    /**
     * helpコマンドを実行する
     * 
     * @param event メッセージ受信イベント
     */
    public void invoke(MessageReceivedEvent event) {
        super.invoke(event, e -> {
            e.getChannel().sendMessage(new MessageCreateBuilder()
                    .setEmbeds(new EmbedBuilder()
                            .setDescription(this.i18n.getString("help.description"))
                            .addField(
                                    this.getCustomizedFieldNameForCommand("help.command.regist"),
                                    this.getCustomizedFieldValueForCommand("help.command.regist.description", Map.of(
                                            "help.command.option.youtube", "help.command.option.youtube.description",
                                            "help.command.option.twitch", "help.command.option.twitch.description")),
                                    false)
                            .addField(this.getCustomizedFieldNameForCommand("help.command.unregist"),
                                    this.getCustomizedFieldValueForCommand("help.command.unregist.description", Map.of(
                                            "help.command.option.youtube", "help.command.option.youtube.description",
                                            "help.command.option.twitch", "help.command.option.twitch.description")),
                                    false)
                            .addField(this.getCustomizedFieldNameForCommand("help.command.lang"),
                                    this.getCustomizedFieldValueForCommand("help.command.lang.description", Map.of(
                                            "help.command.lang.option.en", "help.command.lang.option.en.description",
                                            "help.command.lang.option.ja", "help.command.lang.option.ja.description")),
                                    false)
                            .build())
                    .setTTS(false).build()).queue(success -> {
                        this.changeStatusToNoFailedAndRecordLogResult();
                    },
                            error -> {
                                this.changeStatusToFailedAndRecordLogResult();
                            });
        });
    }

    /**
     * コマンド用にカスタマイズされたEmbedのフィールドネームを取得する
     * 
     * @param key キー
     * @return コマンド用にカスタマイズされたEmbedのフィールドネーム
     */
    private String getCustomizedFieldNameForCommand(String key) {
        return this.prefix.toString() + this.i18n.getString(key);
    }

    /**
     * コマンド用にカスタマイズされたEmbedのフィールドバリューを取得する
     * 
     * @param descriptionKey 説明キー
     * @param optionMap      オプションマップ
     * @return コマンド用にカスタマイズされたEmbedのフィールドバリュー
     */
    private String getCustomizedFieldValueForCommand(String descriptionKey, Map<String, String> optionMap) {
        String fieldValue = """
                %s: %s
                %s:
                """.formatted(this.i18n.getString("help.label.description"), this.i18n.getString(descriptionKey),
                this.i18n.getString("help.label.option"));
        List<String> optionRows = optionMap.entrySet().stream().map(option -> {
            return """
                        `%s%s`: %s
                    """.formatted(this.optionSeparator, this.i18n.getString(option.getKey()),
                    this.i18n.getString(option.getValue()));
        }).collect(Collectors.toList());
        for (String optionRow : optionRows) {
            fieldValue += optionRow;
        }
        return fieldValue;
    };

}
