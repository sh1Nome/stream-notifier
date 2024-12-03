# 仕様書
配信の通知をするDiscord Bot

## 対応プラットフォーム
* Twitch

## コマンド
### 言語設定
* `sn!lang [OPTION]`
    * OPTION
        * `--ja`
        * `--en`

### 通知チャンネル設定
* `sn!notifyMe`
* `sn!notNotifyMe`

### ヘルプ
* `sn!help`

## フローチャート
```mermaid
flowchart TD
    %% コマンド
    cHelp([sn!help])
    cLang([sn!lang])
    cNotifyMe([sn!notifyMe])
    cNotNotifyMe([sn!notNotifyMe])

    %% 出力
    outHelp[/ヘルプを出力する/]

    %% 入力
    setting[/システム設定/]

    %% 処理
    runLang[言語設定をする]
    runNotifyMe[通知チャンネルを設定する]
    runNotNotifyMe[通知チャンネルを解除する]

    %% ノード関係
    cHelp -->|実行| outHelp

    setting -.->|言語設定を取得する| outHelp

    cLang --> runLang --> setting
    cNotifyMe --> runNotifyMe --> setting
    cNotNotifyMe --> runNotNotifyMe --> setting
```