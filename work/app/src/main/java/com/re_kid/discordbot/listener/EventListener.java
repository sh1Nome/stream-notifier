package com.re_kid.discordbot.listener;

import java.util.Optional;

import org.slf4j.Logger;

import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.GenericEvent;

/**
 * 個別イベントリスナーの親クラス
 */
public abstract class EventListener {

    protected final Logger logger;

    protected EventListener(Logger logger) {
        this.logger = logger;
    }

    /**
     * イベントを変換する
     * 
     * @param event      変換前イベントオブジェクト
     * @param eventClazz 変換後イベントクラス
     * @return 変換後イベントのOptional
     */
    protected Optional<? extends Event> convertEvent(GenericEvent event, Class<? extends Event> eventClazz) {
        if (eventClazz.isInstance(event)) {
            return Optional.ofNullable(eventClazz.cast(event));
        }
        return Optional.empty();
    }

    /**
     * イベントを処理する
     * 
     * @param event イベント
     */
    abstract public void onEvent(GenericEvent event);

}
