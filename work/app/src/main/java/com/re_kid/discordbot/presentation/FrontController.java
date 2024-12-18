package com.re_kid.discordbot.presentation;

import org.slf4j.Logger;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;

@Singleton
public class FrontController implements EventListener {

    private final Logger logger;

    @Inject
    public FrontController(Logger logger) {
        this.logger = logger;

    }

    @Override
    public void onEvent(GenericEvent event) {
        if (event instanceof MessageReceivedEvent) {
            MessageReceivedEvent messageReceivedEvent = (MessageReceivedEvent) event;
            String message = messageReceivedEvent.getMessage().getContentRaw();
            logger.info(message);
        }
    }

}
