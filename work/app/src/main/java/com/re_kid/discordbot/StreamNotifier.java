package com.re_kid.discordbot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.re_kid.discordbot.presentation.FrontController;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import net.dv8tion.jda.api.JDA;

@Singleton
public class StreamNotifier {

    private final JDA jda;
    private final FrontController frontController;

    @Inject
    public StreamNotifier(JDA jda, FrontController frontController) {
        this.jda = jda;
        this.frontController = frontController;
    }

    private void run() {
        jda.addEventListener(this.frontController);
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new StreamNotifierModule());
        injector.getInstance(StreamNotifier.class).run();
    }
}
