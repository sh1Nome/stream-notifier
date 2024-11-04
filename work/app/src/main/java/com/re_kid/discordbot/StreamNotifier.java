package com.re_kid.discordbot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.re_kid.discordbot.command.lang.Lang;
import com.re_kid.discordbot.listener.StreamNotifierEventListener;

import net.dv8tion.jda.api.JDA;

/**
 * メインクラス
 */
public class StreamNotifier {

    private final JDA api;
    private final StreamNotifierEventListener streamNotifierEventListener;
    private final Lang lang;

    public StreamNotifier(JDA api, StreamNotifierEventListener streamNotifierEventListener, Lang lang) {
        this.api = api;
        this.streamNotifierEventListener = streamNotifierEventListener;
        this.lang = lang;
    }

    public void run() {
        this.lang.changeActivity();
        this.api.addEventListener(this.streamNotifierEventListener);
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new StreamNotifierModule());
        injector.getInstance(StreamNotifier.class).run();
    }
}
