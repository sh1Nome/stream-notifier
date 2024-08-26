package com.re_kid.discordbot;

import java.util.Optional;

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
        Help("sn!help");

        private final String command;

        private CommandList(String command) {
            this.command = command;
        }

        @Override
        public String toString() {
            return this.command;
        }
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
        if (event.getAuthor().isBot()) {
            return;
        }
        if (CommandList.Help.toString().equals(event.getMessage().getContentRaw())) {
            System.out.println("debug");
        }
    }
}
