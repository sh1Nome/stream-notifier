package com.re_kid.discordbot.listener;

import java.util.Optional;

import org.slf4j.Logger;

import com.re_kid.discordbot.command.Command;
import com.re_kid.discordbot.command.Help;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * メッセージ受信イベント処理クラス
 */
public class MessageReceivedEventListener extends EventListener {

    private final Class<MessageReceivedEvent> eventClazz;
    private final Help help;

    public MessageReceivedEventListener(Class<MessageReceivedEvent> eventClazz, Help help,
            Logger logger) {
        super(logger);
        this.eventClazz = eventClazz;
        this.help = help;
    }

    @Override
    public void onEvent(GenericEvent event) {
        this.convertEvent(event, eventClazz).map(e -> eventClazz.cast(e)).ifPresent(e -> {
            this.onEventHelpCommand(e);
        });
        ;
    }

    /**
     * helpコマンドを処理する
     * 
     * @param event メッセージ受信コマンド
     */
    private void onEventHelpCommand(MessageReceivedEvent event) {
        Optional.ofNullable(event).filter(e -> !e.getAuthor().isBot())
                .filter(e -> help.equals(new Command(e.getMessage()))).ifPresent(e -> {
                    this.recordLogInvokedCommand(help, e.getAuthor());
                    e.getChannel().sendMessage("sn!helpテスト").queue();
                });
    }

    /**
     * コマンドの実行ログを記録する
     * 
     * @param command 実行されたコマンド
     * @param author  コマンド実行者
     */
    private void recordLogInvokedCommand(Command command, User author) {
        logger.info(String.format("Command invoked: %s invoked by %s", command.toString(), author.getName()));
    }

}
