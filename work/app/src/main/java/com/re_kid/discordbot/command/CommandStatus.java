package com.re_kid.discordbot.command;

/**
 * コマンドの状態を表すオブジェクト
 */
public class CommandStatus {

    private boolean failed;

    public CommandStatus(boolean failed) {
        this.failed = failed;
    }

    /**
     * コマンドが失敗状態かどうか確かめる
     * 
     * @return 失敗状態ならtrue
     */
    public boolean isFailed() {
        return this.failed;
    }

    /**
     * 非失敗状態コマンドに設定する
     */
    public void markAsNoFailed() {
        this.failed = false;
    }

    /**
     * 失敗状態コマンドに設定する
     */
    public void markAsFailed() {
        this.failed = true;
    }

}
