# GUIベースの雑誌購読管理システム

> **注記**
>
> 本プロジェクトは、英語圏の大学の環境で開発したものです。コード内のコメントはもともと英語で記述されており、日本語への翻訳には機械翻訳を用いています。翻訳後の内容については、自身で確認・修正を行っています。

## 概要

Java と JavaFX を用いたデスクトップアプリケーションです。雑誌の購読情報を GUI 上で登録・管理でき、終了時にデータを `savedData.ser` へ永続化します。オブジェクト指向設計、FXML による画面構成、Maven によるビルド管理を扱っています。

## 使用技術

- Java 8 / JavaFX
- Maven（Maven Wrapper 同梱）
- Java シリアライゼーション

## 必要な環境

- **Java 8**（JavaFX 同梱の JDK）または **Java 11 以降**
- Maven の個別インストールは不要（`mvnw` / `mvnw.cmd` を同梱）

## ビルド方法

### Windows

```bat
compile.bat
```

### macOS / UNIX

```bash
chmod +x mvnw compile.sh run.sh
./compile.sh
```

## 実行方法

### Windows

```bat
run.bat
```

### macOS / UNIX

```bash
./run.sh
```

## 補足

- エントリーポイント: `src/main/java/personal/assignment2/App.java`
- ソースコードは Java 8（`source` / `target` 1.8）向けにコンパイルされます
- JDK 11 以降では、ビルド時に OpenJFX 11 が Maven により自動取得されます
