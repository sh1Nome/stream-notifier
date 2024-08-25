package com.re_kid.discordbot;

import com.google.inject.Guice;
import com.google.inject.Injector;

import net.dv8tion.jda.api.JDA;

public class StreamNotifier {

    private final JDA jda;

    public StreamNotifier(JDA jda) {
        this.jda = jda;
    }

    public void run() {

    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new StreamNotifierModule());
        injector.getInstance(StreamNotifier.class).run();
    }
}
