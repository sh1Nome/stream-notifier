package com.re_kid.discordbot;

import com.google.inject.Guice;
import com.google.inject.Injector;

import net.dv8tion.jda.api.JDA;

/**
 * メインクラス
 */
public class StreamNotifier {

    private final JDA api;
    private final StreamNotifierEventListener streamNotifierEventListener;

    public StreamNotifier(JDA api, StreamNotifierEventListener streamNotifierEventListener) {
        this.api = api;
        this.streamNotifierEventListener = streamNotifierEventListener;
    }

    public void run() {
        this.api.addEventListener(this.streamNotifierEventListener);
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new StreamNotifierModule());
        injector.getInstance(StreamNotifier.class).run();
    }
}
