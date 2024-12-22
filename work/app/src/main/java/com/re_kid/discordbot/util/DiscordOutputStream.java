package com.re_kid.discordbot.util;

import java.io.IOException;
import java.io.OutputStream;

import jakarta.inject.Singleton;

@Singleton
public class DiscordOutputStream extends OutputStream {

    private final StringBuilder sb;

    public DiscordOutputStream() {
        this.sb = new StringBuilder();
    }

    @Override
    public void write(int b) throws IOException {
        this.sb.append((char) b);
    }

    public String getOutput() {
        String output = this.sb.toString();
        this.sb.setLength(0);
        return output;
    }

}
