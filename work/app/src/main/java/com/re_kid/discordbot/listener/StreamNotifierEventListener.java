package com.re_kid.discordbot.listener;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;

/**
 * イベント処理クラス
 */
public class StreamNotifierEventListener implements EventListener {

    private final MessageReceivedEventListener messageReceivedEventListener;

    public StreamNotifierEventListener(MessageReceivedEventListener messageReceivedEventListener) {
        this.messageReceivedEventListener = messageReceivedEventListener;
    }

    @Override
    public void onEvent(GenericEvent event) {
        messageReceivedEventListener.onEvent(event);
    }

}
