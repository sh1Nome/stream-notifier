package com.re_kid.discordbot.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DiscordPrintWriter extends PrintWriter {

    private final StringWriter stringWriter;
    private final Logger logger;

    @Inject
    public DiscordPrintWriter(Logger logger) {
        super(new StringWriter(), true);
        this.stringWriter = (StringWriter) super.out;
        this.logger = logger;
    }

    public void recordLog() {
        String output = this.stringWriter.toString();
        this.stringWriter.getBuffer().setLength(0);
        if (Strings.isNullOrEmpty(output)) {
            return;
        }
        logger.warn(output);
    }

}
