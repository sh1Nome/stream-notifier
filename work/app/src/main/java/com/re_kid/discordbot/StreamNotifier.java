package com.re_kid.discordbot;

import com.google.inject.Guice;
import com.google.inject.Injector;

import jakarta.inject.Singleton;

@Singleton
public class StreamNotifier {

    private void run() {
        System.out.println("debug");
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new StreamNotifierModule());
        injector.getInstance(StreamNotifier.class).run();
    }
}
