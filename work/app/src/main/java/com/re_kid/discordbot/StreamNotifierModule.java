package com.re_kid.discordbot;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.re_kid.discordbot.event.Help;

import jakarta.inject.Singleton;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class StreamNotifierModule extends AbstractModule {

    @Provides
    @Singleton
    public StreamNotifier provideStreamNotifier(JDA api, Help help) {
        return new StreamNotifier(api, help);
    }

    @Provides
    @Singleton
    public JDA provideJDA() {
        return JDABuilder.createDefault(System.getenv("STREAM_NOTIFIER_TOKEN")).build();
    }

    @Provides
    @Singleton
    public Help provideHelp() {
        return new Help();
    }

}
