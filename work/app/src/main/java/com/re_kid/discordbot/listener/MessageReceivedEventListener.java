package com.re_kid.discordbot.listener;

import java.util.Optional;

import com.re_kid.discordbot.command.Command;
import com.re_kid.discordbot.command.Help;
import com.re_kid.discordbot.command.Prefix;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * メッセージ受信イベント処理クラス
 */
public class MessageReceivedEventListener extends EventListener {

    private final Class<MessageReceivedEvent> eventClazz;
    private final Prefix prefixDefinition;
    private final Help help;

    public MessageReceivedEventListener(Class<MessageReceivedEvent> eventClazz, Prefix prefix, Help help) {
        this.eventClazz = eventClazz;
        this.prefixDefinition = prefix;
        this.help = help;
    }

    @Override
    public void onEvent(GenericEvent event) {
        this.convertEvent(event, eventClazz).map(e -> eventClazz.cast(e)).ifPresent(e -> {
            this.onEventHelpCommand(e);
        });
    }

    /**
     * helpコマンドを処理する
     * 
     * @param event メッセージ受信コマンド
     */
    private void onEventHelpCommand(MessageReceivedEvent event) {
        Optional.ofNullable(event).filter(e -> !e.getAuthor().isBot())
                .filter(e -> help.equals(new Command(e.getMessage(), prefixDefinition))).ifPresent(e -> {
                    help.recordLogInvokedCommand(e.getAuthor());
                    e.getChannel().sendMessage("""
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
                            """).queue();
                });
    }

}
