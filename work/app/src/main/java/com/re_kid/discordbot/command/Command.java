package com.re_kid.discordbot.command;

import org.slf4j.Logger;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

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
    public boolean equals(Command command) {
        if (this.illegal) {
            return false;
        }
        return this.toString().equals(command.toString());
    }

    /**
     * コマンドの実行ログを記録する
     * 
     * @param author コマンド実行者
     */
    public void recordLogInvokedCommand(User author) {
        this.logger.info("Command invoked: " + this.toString() + " invoked by " + author.getName());
    }

}
