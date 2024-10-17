package com.re_kid.discordbot.command.help;

import java.util.Locale;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import com.re_kid.discordbot.I18n;
import com.re_kid.discordbot.command.Command;
import com.re_kid.discordbot.command.CommandStatus;
import com.re_kid.discordbot.command.Prefix;
import com.re_kid.discordbot.command.lang.Lang;
import com.re_kid.discordbot.command.lang.option.En;
import com.re_kid.discordbot.command.lang.option.Ja;
import com.re_kid.discordbot.mapper.SystemSettingMapper;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * helpコマンド
 */
public class Help extends Command {

    private final Lang lang;
    private final En en;
    private final Ja ja;

    public Help(Prefix prefix, String value, CommandStatus commandStatus, String optionSeparator, I18n i18n,
            SqlSessionFactory sqlSessionFactory, Lang lang, En en, Ja ja, Logger logger) {
        super(prefix, value, commandStatus, optionSeparator, i18n, sqlSessionFactory, logger);
        this.lang = lang;
        this.en = en;
        this.ja = ja;
    }

    /**
     * helpコマンドを実行する
     * 
     * @param event メッセージ受信イベント
     */
    public void invoke(MessageReceivedEvent event) {
        super.invoke(event, e -> {
            try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
                String message = "";
                String lang = sqlSession.getMapper(SystemSettingMapper.class).selectById(this.lang.getValue())
                        .getValue();
                if (this.en.getValue().equals(lang)) {
                    message = this.i18n.getString(Locale.ENGLISH, "help.description");
                } else if (this.ja.getValue().equals(lang)) {
                    message = this.i18n.getString(Locale.JAPANESE, "help.description");
                } else {
                    message = this.i18n.getString("help.description");
                }
                e.getChannel().sendMessage(message).queue(success -> {
                    this.changeStatusToNoFailedAndRecordLogResult();
                },
                        error -> {
                            this.changeStatusToFailedAndRecordLogResult();
                        });
            }
        });
    }

}
