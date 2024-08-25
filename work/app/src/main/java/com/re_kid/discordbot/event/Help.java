package com.re_kid.discordbot.event;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class Help implements EventListener {

    @Override
    public void onEvent(GenericEvent event) {
        if (event instanceof MessageReceivedEvent) {
            MessageReceivedEvent e = (MessageReceivedEvent) event;
            System.out.println(e.getMessage().getContentDisplay());
        } else {
            System.out.println("デバッグ2");
        }
    }

}
