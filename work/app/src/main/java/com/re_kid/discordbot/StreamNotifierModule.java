package com.re_kid.discordbot;

import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.re_kid.discordbot.command.CommandStatus;
import com.re_kid.discordbot.command.Help;
import com.re_kid.discordbot.command.Lang;
import com.re_kid.discordbot.command.Prefix;
import com.re_kid.discordbot.listener.MessageReceivedEventListener;
import com.re_kid.discordbot.listener.StreamNotifierEventListener;

import jakarta.inject.Singleton;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;

/**
 * DI設定クラス
 */
public class StreamNotifierModule extends AbstractModule {

    /**
     * ログオブジェクトをDIに設定する
     * 
     * @return ログオブジェクト
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
    public JDA provideJDA(Help help) {
        return JDABuilder.createDefault(System.getenv("STREAM_NOTIFIER_TOKEN"))
                .enableIntents(this.getUpcomingGatewayIntents())
                .setActivity(Activity.of(ActivityType.CUSTOM_STATUS, "Show description: " + help.toString()))
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
     * @param messageReceivedEventListener メッセージ受信イベントリスナー
     * @return イベントリスナー
     */
    @Provides
    @Singleton
    public StreamNotifierEventListener provideStreamNotifierEventListener(
            MessageReceivedEventListener messageReceivedEventListener) {
        return new StreamNotifierEventListener(messageReceivedEventListener);
    }

    /**
     * メッセージ受信イベントリスナーをDIに設定する
     * 
     * @param help helpコマンド
     * @return メッセージ受信イベントリスナー
     */
    @Provides
    @Singleton
    public MessageReceivedEventListener provideMessageReceivedEventListener(Help help, Lang lang) {
        return new MessageReceivedEventListener(MessageReceivedEvent.class, help, lang);
    }

    /**
     * コマンドの接頭辞をDIに設定する
     * 
     * @return コマンドの接頭辞
     */
    @Provides
    @Singleton
    public Prefix providePrefix() {
        return new Prefix("sn", "!");
    }

    /**
     * helpコマンドをDIに登録する
     * 
     * @param prefix コマンドの接頭辞
     * @param logger ログオブジェクト
     * @return helpコマンド
     */
    @Provides
    @Singleton
    public Help provideHelp(Prefix prefix, Logger logger) {
        return new Help(prefix, "help", new CommandStatus(false), logger);
    }

    @Provides
    @Singleton
    public Lang provideLang(Prefix prefix, Logger logger) {
        return new Lang(prefix, "lang", new CommandStatus(false), logger);
    }

}
