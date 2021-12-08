# server-side-kotlin-lounge-template

[Server\-Side Kotlin Lounge \#2「JavaからKotlinへの移行を考える」 \- connpass](https://forkwell.connpass.com/event/229732/) で使用するテンプレのリポジトリです。

## 前提条件

下記をインストールしてください。

* [Oracle JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [Maven 3.8.4](https://maven.apache.org/download.cgi)
* [Gauge 1.4.2](https://docs.gauge.org/getting_started/installing-gauge.html)
* [Docker](https://docs.docker.com/get-docker/) もしくは Postgresql

## 目標

以下のサンプルのドメインを実装してみます。

* Qiita、Zenn、はてブなど、記事を投稿できるさまざまなサービスのユーザを一括で管理するためのシステムを作ります
* 以下のような手順で利用できる Web API として作ります
    1. `POST /users` : ユーザを作ります
    1. `PUT /users/{userId}` : ユーザに Qiita アカウントなどを紐付けます
    1. `GET /articles?userId={userId}` : ユーザに紐付いてる全アカウントの全記事を取得します

IntelliJ IDEA に Gauge プラグインを入れた上で、`e2e/specs/users.spec` を見ていただくと、上記仕様に関する具体的な情報を得られるかもしれません。

## アプリ構築

以下のライブラリ / フレームワーク / ソフトウェアを使って Web API を構築します。

* Ktor: Web フレームワーク
* Koin: DI フレームワーク
* Fuel: HTTP 通信用ライブラリ
* Exposed: O/R Mapper

また、テスト用に以下を使用します。

* Kotest: 単体テスト用フレームワーク
* mockk: 単体テスト用のモッキングライブラリ
* Gauge: 自動テスト作成用のフレームワーク、E2E テストの作成に使用

## アプリの起動など

### データベースの起動

```shell
cd database
docker compose up

# 停止
docker compose down -v --rmi all
```

### server の起動

exec-maven-plugin の導入をサボったので、IntelliJ IDEA から `server/src/main/kotlin/me/takehara/rest/Application.kt` の `main` を実行してください。

### E2E テストの実行

gauge-maven-plugin により Gauge のテストを全実行できます。

```shell
cd e2e
mvn clean test

# unimplemented (アプリが未実装のテスト) を除外する場合
mvn clean test -Dtags='!unimplemented'
```
