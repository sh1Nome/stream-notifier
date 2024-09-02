package com.re_kid.discordbot.command;

import org.slf4j.Logger;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * helpコマンド
 */
public class Help extends Command {

    public Help(Prefix prefix, String value, boolean illegal, Logger logger) {
        super(prefix, value, illegal, logger);
    }

    /**
     * helpコマンドを実行する
     * 
     * @param event メッセージ受信イベント
     */
    public void invoke(MessageReceivedEvent event) {
        super.invoke(event, e -> {
            e.getChannel().sendMessage("""
                    This bot sends notifications for streams.

                    Command: `/sn-regist [OPTION] [ACCOUNT]`
                    Description: Register your account to StreamNotifier
                    Options:
                        `--youtube`: YouTube Account
                        `--twitch`: Twitch Account

                    Command: `/sn-unregist [OPTION]`
                    Description: Unregister your account to StreamNotifier
                    Options:
                        `--youtube`: YouTube Account
                        `--twitch`: Twitch Account
                    """).queue(message -> super.recordLogSuccessfulCommand(), error -> super.recordLogFailedCommand());
        });
    }

}
