# 仕様書
配信の通知をするDiscord Bot

## 対応プラットフォーム
* Twitch

## コマンド
### 言語設定
言語設定をする  
```
sn!lang [OPTION]
```

#### OPTION
|key|args|detail|
|:--|:--|:--|
|ja|-|日本語|
|en|-|英語|

### 通知チャンネル設定
通知チャンネルを設定する  
```
sn!notifyMe
```

通知チャンネル設定を解除する
```
sn!notNotifyMe
```

### ヘルプ
ヘルプ表示する
```
sn!help
```

## フローチャート
```mermaid
graph TD
    %% コマンド
    cHelp([sn!help])
    cLang([sn!lang])
    cNotifyMe([sn!notifyMe])
    cNotNotifyMe([sn!notNotifyMe])

    %% 出力
    outHelp[ヘルプを出力する]

    %% 入力
    setting[システム設定]

    %% ノード関係
    cHelp -->|実行| outHelp

    setting -.->|言語設定を取得する| outHelp

    cLang -->|言語設定をする| setting
    cNotifyMe -->|通知チャンネルを設定する| setting
    cNotNotifyMe -->|通知チャンネルを解除する| setting
```