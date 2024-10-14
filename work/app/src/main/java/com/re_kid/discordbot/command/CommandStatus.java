package com.re_kid.discordbot.command;

/**
 * コマンドの状態を表すオブジェクト
 */
public class CommandStatus {

    private boolean illegal;

    public CommandStatus(boolean illegal) {
        this.illegal = illegal;
    }

    /**
     * コマンドが違反かどうか確かめる
     * 
     * @return 違反ならtrue
     */
    public boolean isIllegal() {
        return this.illegal;
    }

    /**
     * 非違反コマンドに設定する
     */
    public void markAsNoIllegal() {
        this.illegal = false;
    }

    /**
     * 違反コマンドに設定する
     */
    public void markAsIllegal() {
        this.illegal = true;
    }

}
