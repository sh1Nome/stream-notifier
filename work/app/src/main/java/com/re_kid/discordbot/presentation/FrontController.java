package com.re_kid.discordbot.presentation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.re_kid.discordbot.presentation.command.HelpController;
import com.re_kid.discordbot.presentation.command.LangController;
import com.re_kid.discordbot.presentation.command.NotNotifyMeController;
import com.re_kid.discordbot.presentation.command.NotifyMeController;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;

@Singleton
public class FrontController implements EventListener {

    private final String commandPrefix;
    private final Map<String, Controller> dispatch;

    private final Logger logger;

    @Inject
    public FrontController(HelpController helpController, LangController langController,
            NotifyMeController notifyMeController, NotNotifyMeController notNotifyMeController, Logger logger) {
        this.commandPrefix = "sn!";

        this.dispatch = new HashMap<>();
        this.dispatch.put("help", helpController);
        this.dispatch.put("lang", langController);
        this.dispatch.put("notifyMe", notifyMeController);
        this.dispatch.put("notNotifyMe", notNotifyMeController);

        this.logger = logger;
    }

    @Override
    public void onEvent(GenericEvent event) {
        if (event instanceof MessageReceivedEvent) {
            MessageReceivedEvent messageReceivedEvent = (MessageReceivedEvent) event;
            String message = messageReceivedEvent.getMessage().getContentRaw();
            String compare = Arrays.asList(message.split(" ")).getFirst();
            this.dispatch.entrySet().stream()
                    .filter((map) -> {
                        String command = this.commandPrefix + map.getKey();
                        return command.equals(compare);
                    })
                    .findFirst()
                    .map(Map.Entry<String, Controller>::getValue)
                    .ifPresent((controller) -> controller.execute(null));
            logger.info(message);
        }
    }

}
