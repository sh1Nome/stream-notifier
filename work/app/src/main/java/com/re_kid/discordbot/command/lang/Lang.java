package com.re_kid.discordbot.command.lang;

import org.slf4j.Logger;

import com.re_kid.discordbot.command.Command;
import com.re_kid.discordbot.command.CommandStatus;
import com.re_kid.discordbot.command.Prefix;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Lang extends Command {

    public Lang(Prefix prefix, String value, CommandStatus commandStatus, Logger logger) {
        super(prefix, value, commandStatus, logger);
    }

    public void invoke(MessageReceivedEvent event) {
        super.invoke(event, e -> {
            return this.commandStatus;
        });
    }

}
