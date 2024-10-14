package com.re_kid.discordbot.command.lang;

import java.util.List;

import org.slf4j.Logger;

import com.re_kid.discordbot.command.Command;
import com.re_kid.discordbot.command.CommandStatus;
import com.re_kid.discordbot.command.Option;
import com.re_kid.discordbot.command.Prefix;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Lang extends Command {

    public Lang(Prefix prefix, String value, CommandStatus commandStatus, List<Option> options, String optionSeparator,
            Logger logger) {
        super(prefix, value, commandStatus, optionSeparator, options, logger);
    }

    public void invoke(MessageReceivedEvent event) {
        super.invoke(event, e -> {
            return this.commandStatus;
        });
    }

}
