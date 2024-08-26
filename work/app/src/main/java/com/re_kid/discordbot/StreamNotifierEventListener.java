package com.re_kid.discordbot;

import java.util.Optional;

import org.slf4j.Logger;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;

/**
 * イベント処理クラス
 */
public class StreamNotifierEventListener implements EventListener {

    /**
     * コマンドリスト
     */
    private static enum CommandList {
        Help("help");

        private final String prefix = "sn!";

        private final String command;

        private CommandList(String command) {
            this.command = command;
        }

        @Override
        public String toString() {
            return this.prefix + this.command;
        }

    }

    private final Logger logger;

    public StreamNotifierEventListener(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void onEvent(GenericEvent event) {
        this.convertEvent(event, MessageReceivedEvent.class)
                .ifPresent(e -> this.onMessageReceivedEvent((MessageReceivedEvent) e));
    }

    /**
     * イベントを変換する
     * 
     * @param event         変換前イベントオブジェクト
     * @param mapEventClazz 変換後イベントクラス
     * @return 変換後イベントのOptional
     */
    private Optional<? extends Event> convertEvent(GenericEvent event, Class<? extends Event> mapEventClazz) {
        if (mapEventClazz.isInstance(event)) {
            return Optional.ofNullable(mapEventClazz.cast(event));
        }
        return Optional.empty();
    }

    /**
     * メッセージ受信時のイベントを処理する
     * 
     * @param event メッセージ受信時のイベント
     */
    private void onMessageReceivedEvent(MessageReceivedEvent event) {
        User author = event.getAuthor();
        CommandList help = CommandList.Help;
        Optional.ofNullable(event).filter(e -> !author.isBot())
                .filter(e -> help.toString().equals(e.getMessage().getContentRaw()))
                .ifPresent(e -> {
                    this.recordLogInvokedCommand(help, author);
                    event.getChannel().sendMessage("sn!helpテスト").queue();
                });
    }

    /**
     * コマンドの実行ログを記録する
     * 
     * @param command 実行されたコマンド
     * @param author  コマンド実行者
     */
    private void recordLogInvokedCommand(CommandList command, User author) {
        logger.info(String.format("Command invoked: %s invoked by %s", command.toString(), author.getName()));
    }
}
