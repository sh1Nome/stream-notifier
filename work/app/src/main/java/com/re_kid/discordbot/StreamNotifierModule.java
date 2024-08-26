package com.re_kid.discordbot;

import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import jakarta.inject.Singleton;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

/**
 * DI設定クラス
 */
public class StreamNotifierModule extends AbstractModule {

    /**
     * ログクラスをDIに設定する
     * 
     * @return ログクラス
     */
    @Provides
    @Singleton
    public Logger provideLogger() {
        return LoggerFactory.getLogger(StreamNotifier.class);
    }

    /**
     * 実行クラスをDIに設定する
     * 
     * @param api                         JDA
     * @param streamNotifierEventListener イベントリスナー
     * @return 実行クラス
     */
    @Provides
    @Singleton
    public StreamNotifier provideStreamNotifier(JDA api, StreamNotifierEventListener streamNotifierEventListener) {
        return new StreamNotifier(api, streamNotifierEventListener);
    }

    /**
     * JDAをDIに設定する
     * 
     * @return JDA
     */
    @Provides
    @Singleton
    public JDA provideJDA() {
        return JDABuilder.createDefault(System.getenv("STREAM_NOTIFIER_TOKEN"))
                .enableIntents(this.getUpcomingGatewayIntents())
                .build();
    }

    /**
     * 追加するGatewayIntentsを取得する
     * 
     * @return 追加するGatewayIntents
     */
    private Collection<GatewayIntent> getUpcomingGatewayIntents() {
        Collection<GatewayIntent> gatewayIntents = new HashSet<>();
        gatewayIntents.add(GatewayIntent.MESSAGE_CONTENT);
        return gatewayIntents;
    }

    /**
     * イベントリスナーをDIに設定する
     * 
     * @param logger ログクラス
     * 
     * @return イベントリスナー
     */
    @Provides
    @Singleton
    public StreamNotifierEventListener provideHelp(Logger logger) {
        return new StreamNotifierEventListener(logger);
    }

}
