package com.re_kid.discordbot.command.lang;

import org.slf4j.Logger;

import com.re_kid.discordbot.command.Command;
import com.re_kid.discordbot.command.CommandStatus;
import com.re_kid.discordbot.command.Prefix;
import com.re_kid.discordbot.command.lang.option.En;
import com.re_kid.discordbot.command.lang.option.Ja;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Lang extends Command {

    private final En en;
    private final Ja ja;

    public Lang(Prefix prefix, String value, CommandStatus commandStatus, String optionSeparator, En en, Ja ja,
            Logger logger) {
        super(prefix, value, commandStatus, optionSeparator, logger);
        this.en = en;
        this.ja = ja;
    }

    public void invoke(MessageReceivedEvent event) {
        super.invoke(event, e -> {
            return this.commandStatus;
        });
    }

}
