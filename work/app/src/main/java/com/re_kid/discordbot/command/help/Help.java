package com.re_kid.discordbot.command.help;

import org.slf4j.Logger;
import java.util.Locale;

import com.re_kid.discordbot.I18n;
import com.re_kid.discordbot.command.Command;
import com.re_kid.discordbot.command.CommandStatus;
import com.re_kid.discordbot.command.Prefix;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * helpコマンド
 */
public class Help extends Command {

    public Help(Prefix prefix, String value, CommandStatus commandStatus, String optionSeparator, I18n i18n,
            Logger logger) {
        super(prefix, value, commandStatus, optionSeparator, i18n, logger);
    }

    /**
     * helpコマンドを実行する
     * 
     * @param event メッセージ受信イベント
     */
    public void invoke(MessageReceivedEvent event) {
        super.invoke(event, e -> {
            e.getChannel().sendMessage(this.i18n.getString(Locale.ENGLISH, "help.description")).queue(success -> {
                this.changeStatusToNoFailedAndRecordLogResult();
            },
                    error -> {
                        this.changeStatusToFailedAndRecordLogResult();
                    });
        });
    }

}
