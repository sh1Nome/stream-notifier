package com.re_kid.discordbot;

import com.google.inject.Guice;
import com.google.inject.Injector;

import net.dv8tion.jda.api.JDA;

/**
 * メインクラス
 */
public class StreamNotifier {

    private final JDA api;
    private final StreamNotifierEventListener help;

    public StreamNotifier(JDA api, StreamNotifierEventListener help) {
        this.api = api;
        this.help = help;
    }

    public void run() {
        this.api.addEventListener(this.help);
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new StreamNotifierModule());
        injector.getInstance(StreamNotifier.class).run();
    }
}
