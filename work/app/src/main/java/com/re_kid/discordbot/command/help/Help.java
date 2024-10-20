package com.re_kid.discordbot.command.help;

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
                            .setDescription(this.i18n.getString("help.description")).build())
                    .setTTS(false).build()).queue(success -> {
                        this.changeStatusToNoFailedAndRecordLogResult();
                    },
                            error -> {
                                this.changeStatusToFailedAndRecordLogResult();
                            });
        });
    }

}
