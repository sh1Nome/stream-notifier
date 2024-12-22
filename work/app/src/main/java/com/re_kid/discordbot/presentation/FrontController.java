package com.re_kid.discordbot.presentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;

import com.re_kid.discordbot.util.DiscordPrintWriter;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import picocli.CommandLine;

@Singleton
public class FrontController implements EventListener {

    private final SN sn;
    private final Logger logger;
    private final DiscordPrintWriter discordPrintWriter;

    @Inject
    public FrontController(SN sn, Logger logger, DiscordPrintWriter discordPrintWriter) {
        this.sn = sn;
        this.logger = logger;
        this.discordPrintWriter = discordPrintWriter;
    }

    @Override
    public void onEvent(GenericEvent event) {
        if (event instanceof MessageReceivedEvent) {
            MessageReceivedEvent messageReceivedEvent = (MessageReceivedEvent) event;
            String message = messageReceivedEvent.getMessage().getContentRaw();
            List<String> splitedMessages = new ArrayList<>(Arrays.asList(message.split(" ")));
            if (sn.toString().equals(splitedMessages.remove(0))) {
                new CommandLine(sn).setOut(this.discordPrintWriter).execute(splitedMessages.toArray(new String[0]));
            }
            logger.info(message);
            logger.warn(this.discordPrintWriter.getOutput());
        }
    }

}
