package com.re_kid.discordbot;

import java.util.Collection;
import java.util.HashSet;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import jakarta.inject.Singleton;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

/**
 * DI設定
 */
public class StreamNotifierModule extends AbstractModule {

    /**
     * 実行クラス
     * 
     * @param api  JDAライブラリ
     * @param help helpコマンド
     * @return 実行クラス
     */
    @Provides
    @Singleton
    public StreamNotifier provideStreamNotifier(JDA api, StreamNotifierEventListener help) {
        return new StreamNotifier(api, help);
    }

    /**
     * JDAライブラリ
     * 
     * @return JDAライブラリ
     */
    @Provides
    @Singleton
    public JDA provideJDA() {
        return JDABuilder.createDefault(System.getenv("STREAM_NOTIFIER_TOKEN"))
                .enableIntents(this.addGatewayIntents())
                .build();
    }

    private Collection<GatewayIntent> addGatewayIntents() {
        Collection<GatewayIntent> gatewayIntents = new HashSet<>();
        gatewayIntents.add(GatewayIntent.MESSAGE_CONTENT);
        return gatewayIntents;
    }

    /**
     * helpコマンド
     * 
     * @return helpコマンド
     */
    @Provides
    @Singleton
    public StreamNotifierEventListener provideHelp() {
        return new StreamNotifierEventListener();
    }

}
