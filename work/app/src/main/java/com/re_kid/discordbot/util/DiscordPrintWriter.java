package com.re_kid.discordbot.util;

import java.io.PrintWriter;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DiscordPrintWriter extends PrintWriter {

    private final DiscordOutputStream discordOutputStream;

    @Inject
    public DiscordPrintWriter(DiscordOutputStream discordOutputStream) {
        super(discordOutputStream);
        this.discordOutputStream = discordOutputStream;
    }

    public String getOutput() {
        return discordOutputStream.getOutput();
    }
    
}
