package com.re_kid.discordbot.command;

import java.util.Optional;
import java.util.function.Function;

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
    protected CommandStatus commandStatus;
    protected Logger logger;

    public Command(Prefix prefix, String value, CommandStatus commandStatus, Logger logger) {
        this.prefix = prefix;
        this.value = value;
        this.commandStatus = commandStatus;
        this.logger = logger;
    }

    public Command(Message message, Prefix prefixDefinition) {
        String[] command = message.getContentRaw().split(prefixDefinition.getSeparator());
        if (2 == command.length) {
            this.prefix = new Prefix(command[0], prefixDefinition.getSeparator());
            this.value = command[1];
            this.commandStatus = new CommandStatus(false);
        } else {
            this.commandStatus = new CommandStatus(true);
        }
    }

    @Override
    public String toString() {
        if (this.commandStatus.isIllegal()) {
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
        if (this.commandStatus.isIllegal()) {
            return false;
        }
        return this.toString().equals(command.toString());
    }

    /**
     * コマンドを実行する
     * 
     * @param event  メッセージ受信イベント
     * @param action 継承時に定義する干渉アクション
     */
    protected void invoke(MessageReceivedEvent event, Function<MessageReceivedEvent, CommandStatus> action) {
        this.validate(event).ifPresent(e -> {
            this.recordLogInvokedCommand(e.getAuthor());
            this.recordLogResultCommand(action.apply(e));
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
                .filter(generatedCommandFromMessage -> !generatedCommandFromMessage.commandStatus.isIllegal())
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
     * コマンドの実行結果ログを記録する
     * 
     * @param commandStatus コマンドの状態
     */
    private void recordLogResultCommand(CommandStatus commandStatus) {
        if (commandStatus.isIllegal()) {
            this.recordLogFailedCommand();
            return;
        }
        this.recordLogSuccessfulCommand();
    }

    /**
     * コマンドの成功ログを記録する
     */
    private void recordLogSuccessfulCommand() {
        this.logger.info("Command Successful!");
    }

    /**
     * コマンドの失敗ログを記録する
     */
    private void recordLogFailedCommand() {
        this.logger.info("Command Failed!");
    }

}
