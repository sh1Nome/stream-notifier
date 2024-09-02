package com.re_kid.discordbot.command;

import java.util.Optional;
import java.util.function.Consumer;

import org.slf4j.Logger;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * コマンドの親クラス
 */
public class Command {

    protected Prefix prefix;
    protected String value;
    protected boolean illegal;
    protected Logger logger;

    public Command(Prefix prefix, String value, boolean illegal, Logger logger) {
        this.prefix = prefix;
        this.value = value;
        this.illegal = illegal;
        this.logger = logger;
    }

    public Command(Message message, Prefix prefixDefinition) {
        String[] command = message.getContentRaw().split(prefixDefinition.getSeparator());
        if (2 == command.length) {
            this.prefix = new Prefix(command[0], prefixDefinition.getSeparator());
            this.value = command[1];
            this.illegal = false;
        } else {
            this.illegal = true;
        }
    }

    @Override
    public String toString() {
        if (this.illegal) {
            return "";
        }
        return this.prefix.toString() + this.value;
    }

    /**
     * コマンドが等しいかどうか確かめる
     * 
     * @param command 確かめるコマンド
     * @return 等しければtrue
     */
    private boolean equals(Command command) {
        if (this.illegal) {
            return false;
        }
        return this.toString().equals(command.toString());
    }

    /**
     * 違反コマンドか調べる
     * 
     * @return 違反ならtrue
     */
    private boolean isIllegal() {
        return this.illegal;
    }

    /**
     * コマンドを実行する
     * 
     * @param event  メッセージ受信イベント
     * @param action 継承時に定義する非干渉アクション
     */
    protected void invoke(MessageReceivedEvent event, Consumer<MessageReceivedEvent> action) {
        this.validate(event).ifPresent(e -> {
            this.recordLogInvokedCommand(e.getAuthor());
            action.accept(event);
        });
    }

    /**
     * コマンドが実行可能か判断する
     * 
     * @param event メッセージ受信イベント
     * 
     * @return 実行可能ならメッセージ受信イベントのOptional、実行不可なら空のOptional
     */
    private Optional<MessageReceivedEvent> validate(MessageReceivedEvent event) {
        return Optional.ofNullable(event).filter(e -> !e.getAuthor().isBot())
                .map(e -> new Command(event.getMessage(), this.prefix))
                .filter(generatedCommandFromMessage -> !generatedCommandFromMessage.isIllegal())
                .filter(generatedCommandFromMessage -> generatedCommandFromMessage.equals(this))
                .map(c -> event);
    }

    /**
     * コマンドの実行ログを記録する
     * 
     * @param author コマンド実行者
     */
    private void recordLogInvokedCommand(User author) {
        this.logger.info("Command invoked: " + this.toString() + " invoked by " + author.getName());
    }

    /**
     * コマンドの成功ログを記録する
     */
    protected void recordLogSuccessfulCommand() {
        this.logger.info("Command Successful!");
    }

    /**
     * コマンドの失敗ログを記録する
     */
    protected void recordLogFailedCommand() {
        this.logger.info("Command Failed!");
    }

}
