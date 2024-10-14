package com.re_kid.discordbot.command.lang;

import java.util.Arrays;

import org.slf4j.Logger;

import com.re_kid.discordbot.command.Command;
import com.re_kid.discordbot.command.CommandStatus;
import com.re_kid.discordbot.command.Option;
import com.re_kid.discordbot.command.Prefix;
import com.re_kid.discordbot.command.lang.option.En;
import com.re_kid.discordbot.command.lang.option.Ja;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;

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
            Arrays.asList(event.getMessage().getContentRaw().split(" ")).stream()
                    .filter(splitMessage -> splitMessage.startsWith(this.optionSeparator))
                    .map(optionMessage -> new Option(optionMessage.replace(this.optionSeparator, "")))
                    .filter(generatedOptionFromMessage -> {
                        return en.equals(generatedOptionFromMessage)
                                || ja.equals(generatedOptionFromMessage);
                    })
                    .findFirst()
                    .ifPresentOrElse(
                            scheduledOption -> this.executeOption(scheduledOption, e).queue(
                                    success -> {
                                        this.changeStatusToNoFailedAndRecordLogResult();
                                    },
                                    error -> {
                                        this.changeStatusToFailedAndRecordLogResult();
                                    }),
                            () -> {
                                this.changeStatusToFailedAndRecordLogResult();
                            });
        });
    }

    private MessageCreateAction executeOption(Option scheduledOption, MessageReceivedEvent event) {
        if (en.equals(scheduledOption)) {
            return executeEnOption(event);
        } else {
            return executeJaOption(event);
        }
    }

    private MessageCreateAction executeEnOption(MessageReceivedEvent event) {
        return event.getChannel().sendMessage("""
                test en
                """);
    }

    private MessageCreateAction executeJaOption(MessageReceivedEvent event) {
        return event.getChannel().sendMessage("""
                test ja
                """);
    }

}
