package com.re_kid.discordbot.command;

import java.util.Optional;
import java.util.function.Consumer;

import org.slf4j.Logger;

import com.re_kid.discordbot.I18n;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * コマンドの親クラス
 */
public class Command {

    protected Prefix prefix;
    protected String value;
    protected final CommandStatus commandStatus;
    protected String optionSeparator;
    protected I18n i18n;
    protected Logger logger;

    public Command(Prefix prefix, String value, CommandStatus commandStatus, String optionSeparator,
            I18n i18n, Logger logger) {
        this.prefix = prefix;
        this.value = value;
        this.commandStatus = commandStatus;
        this.optionSeparator = optionSeparator;
        this.i18n = i18n;
        this.logger = logger;
    }

    public Command(Message message, Prefix prefixDefinition) {
        String[] command = message.getContentRaw().split(prefixDefinition.getSeparator());
        command[1] = command[1].split(" ")[0];
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
        return this.prefix.toString() + this.value;
    }

    /**
     * コマンドが等しいかどうか確かめる
     * 
     * @param command 確かめるコマンド
     * @return 等しければtrue
     */
    private boolean equals(Command command) {
        return this.toString().equals(command.toString());
    }

    /**
     * コマンドを実行する
     * 
     * @param event  メッセージ受信イベント
     * @param action 継承時に定義する干渉アクション
     */
    protected void invoke(MessageReceivedEvent event, Consumer<MessageReceivedEvent> action) {
        this.validate(event).ifPresent(e -> {
            this.recordLogInvokedCommand(e.getAuthor());
            action.accept(e);
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
     * コマンドを失敗状態に変更し、実行ログを記録する
     */
    protected void changeStatusToFailedAndRecordLogResult() {
        this.commandStatus.markAsFailed();
        this.recordLogResultCommand();
    }

    /**
     * コマンドを非失敗状態に変更し、実行ログを記録する
     */
    protected void changeStatusToNoFailedAndRecordLogResult() {
        this.commandStatus.markAsNoFailed();
        this.recordLogResultCommand();
    }

    /**
     * コマンドの実行結果ログを記録する
     * 
     * @param commandStatus コマンドの状態
     */
    private void recordLogResultCommand() {
        if (this.commandStatus.isFailed()) {
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
        this.logger.warn("Command Failed!");
    }

}
