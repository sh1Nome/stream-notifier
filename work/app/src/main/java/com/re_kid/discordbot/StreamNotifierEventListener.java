package com.re_kid.discordbot;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;

/**
 * イベント処理クラス
 */
public class StreamNotifierEventListener implements EventListener {

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
        if (event instanceof MessageReceivedEvent) {
            this.onMessageReceivedEvent((MessageReceivedEvent) event);
        }
    }

    private void onMessageReceivedEvent(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        if (CommandList.Help.toString().equals(event.getMessage().getContentRaw())) {
            System.out.println("debug");
        }
    }
}
