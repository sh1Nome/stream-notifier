package com.re_kid.discordbot;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.re_kid.discordbot.command.Prefix;
import com.re_kid.discordbot.command.help.Help;
import com.re_kid.discordbot.command.lang.Lang;
import com.re_kid.discordbot.command.lang.option.En;
import com.re_kid.discordbot.command.lang.option.Ja;
import com.re_kid.discordbot.command.notifyme.NotifyMe;
import com.re_kid.discordbot.command.notnotifyme.NotNotifyMe;
import com.re_kid.discordbot.db.repository.SystemSettingRepository;
import com.re_kid.discordbot.listener.MessageReceivedEventListener;
import com.re_kid.discordbot.listener.StreamNotifierEventListener;

import jakarta.inject.Singleton;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
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
     * i18nオブジェクトをDIに設定する
     * 
     * @param systemSettingRepository TODO
     * 
     * @return i18nオブジェクト
     */
    @Provides
    @Singleton
    public I18n provideI18n(SystemSettingRepository systemSettingRepository) {
        return new I18n(systemSettingRepository);
    }

    /**
     * データベース接続情報を取得する
     * 
     * @return データベース接続情報
     */
    private Properties getDbConnectInfo() {
        Properties prop = new Properties();
        prop.put("driver", "org.postgresql.Driver");
        prop.put("url",
                "jdbc:postgresql://" + System.getenv("POSTGRES_HOST") + ":" + System.getenv("POSTGRES_PORT") + "/"
                        + System.getenv("POSTGRES_DB"));
        prop.put("username", System.getenv("POSTGRES_USER"));
        prop.put("password", System.getenv("POSTGRES_PASSWORD"));
        return prop;
    }

    /**
     * Mybatisで利用するSqlSessionFactoryをDIに設定する
     * 
     * @return SqlSessionFactory
     * @throws IOException
     */
    @Provides
    @Singleton
    public SqlSessionFactory provideSqlSessionFactory() throws IOException {
        return new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("db/mapper/mybatis-config.xml"),
                this.getDbConnectInfo());
    }

    /**
     * @param sqlSessionFactory
     * @return
     */
    @Provides
    @Singleton
    public SystemSettingRepository provideSystemSettingRepository(SqlSessionFactory sqlSessionFactory) {
        return new SystemSettingRepository(sqlSessionFactory);
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
    public StreamNotifier provideStreamNotifier(JDA api, StreamNotifierEventListener streamNotifierEventListener,
            Lang lang) {
        return new StreamNotifier(api, streamNotifierEventListener, lang);
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
     * @param help        helpコマンド
     * @param lang        langコマンド
     * @param notifyMe    notifyMeコマンド
     * @param notNotifyMe TODO
     * @return メッセージ受信イベントリスナー
     */
    @Provides
    @Singleton
    public MessageReceivedEventListener provideMessageReceivedEventListener(Help help, Lang lang, NotifyMe notifyMe,
            NotNotifyMe notNotifyMe) {
        return new MessageReceivedEventListener(MessageReceivedEvent.class, help, lang, notifyMe, notNotifyMe);
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
     * コマンドオプションのセパレーター
     */
    private final String optionSeparator = "--";

    /**
     * helpコマンドをDIに登録する
     * 
     * @param prefix コマンドの接頭辞
     * @param logger ログオブジェクト
     * @return helpコマンド
     */
    @Provides
    @Singleton
    public Help provideHelp(Prefix prefix, I18n i18n, SystemSettingRepository systemSettingRepository, Logger logger) {
        return new Help(prefix, "help", this.optionSeparator, i18n, systemSettingRepository, logger);
    }

    /**
     * langコマンドのenオプションをDIに設定する
     * 
     * @return langコマンドのenオプション
     */
    @Provides
    @Singleton
    public En provideEn() {
        return new En("en");
    }

    /**
     * langコマンドのjaオプションをDIに設定する
     * 
     * @return langコマンドのjaオプション
     */
    @Provides
    @Singleton
    public Ja provideJa() {
        return new Ja("ja");
    }

    /**
     * langコマンドをDIに設定する
     * 
     * @param prefix                  コマンドの接頭辞
     * @param systemSettingRepository TODO
     * @param logger                  ログオブジェクト
     * 
     * @return langコマンド
     */
    @Provides
    @Singleton
    public Lang provideLang(Prefix prefix, En en, Ja ja, Help help, JDA jda,
            SystemSettingRepository systemSettingRepository,
            I18n i18n,
            Logger logger) {
        return new Lang(prefix, "lang", this.optionSeparator, en, ja, help, jda, systemSettingRepository, i18n, logger);
    }

    /**
     * notifyMeコマンドをDIに設定する
     * 
     * @param prefix                  コマンドの接頭辞
     * @param i18n                    i18nオブジェクト
     * @param systemSettingRepository TODO
     * @param logger                  ログオブジェクト
     * 
     * @return
     */
    @Provides
    @Singleton
    public NotifyMe provideNotifyMe(Prefix prefix, I18n i18n, SystemSettingRepository systemSettingRepository,
            Logger logger) {
        return new NotifyMe(prefix, "notifyMe", this.optionSeparator, i18n, systemSettingRepository, logger);
    }

    /**
     * notNotifyMeコマンドをDIに設定する
     * 
     * @param prefix
     * @param i18n
     * @param systemSettingRepository
     * @param logger
     * @return
     */
    @Provides
    @Singleton
    public NotNotifyMe provideNotNotifyMe(Prefix prefix, I18n i18n, SystemSettingRepository systemSettingRepository,
            Logger logger) {
        return new NotNotifyMe(prefix, "notNotifyMe", optionSeparator, i18n, systemSettingRepository, logger);
    }

}
