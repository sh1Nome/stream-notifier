package com.re_kid.discordbot.command;

import net.dv8tion.jda.api.entities.Message;

/**
 * コマンドの親クラス
 */
public class Command {

    protected Prefix prefix;
    protected String value;
    protected boolean illegal;

    public Command(Prefix prefix, String value, boolean illegal) {
        this.prefix = prefix;
        this.value = value;
        this.illegal = illegal;
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

}
