package com.re_kid.discordbot.listener;

import com.re_kid.discordbot.command.help.Help;
import com.re_kid.discordbot.command.lang.Lang;
import com.re_kid.discordbot.command.notifyme.NotifyMe;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * メッセージ受信イベント処理クラス
 */
public class MessageReceivedEventListener extends EventListener {

    private final Class<MessageReceivedEvent> eventClazz;
    private final Help help;
    private final Lang lang;
    private final NotifyMe notifyMe;

    public MessageReceivedEventListener(Class<MessageReceivedEvent> eventClazz, Help help, Lang lang,
            NotifyMe notifyMe) {
        this.eventClazz = eventClazz;
        this.help = help;
        this.lang = lang;
        this.notifyMe = notifyMe;
    }

    @Override
    public void onEvent(GenericEvent event) {
        this.convertEvent(event, eventClazz).map(e -> eventClazz.cast(e)).ifPresent(e -> {
            help.invoke(e);
            lang.invoke(e);
            notifyMe.invoke(e);
        });
    }

}
