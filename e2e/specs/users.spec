# ユーザに関するテスト

## ユーザを新規作成できる
tags: unimplemented
* DBのレコードをすべて削除する
* URL"http://localhost:8080/users"にボディ<file:jsons/create-user.json>で、POSTリクエストを送る
* レスポンスステータスコードが"201"である
* レスポンスのJSONの"$.id"が文字列の"m-takehara"である

## ユーザにQiitaアカウントを紐付けられる
tags: unimplemented
* DBのレコードをすべて削除する
* DBにユーザ"m-takehara"のレコードを挿入する
* URL"http://localhost:8080/users/m-takehara"にボディ<file:jsons/link-qiita.json>で、PUTリクエストを送る
* レスポンスステータスコードが"200"である
* DB"demo_db"の"demo_schema"スキーマの"accounts"テーブルの、"user_id"を"'m-takehara'"で取得した一意の"account_id"が文字列の"m-takehara"である
* DB"demo_db"の"demo_schema"スキーマの"accounts"テーブルの、"user_id"を"'m-takehara'"で取得した一意の"service"が文字列の"qiita"である

## ユーザにZennアカウントを紐付けられる
tags: unimplemented
* DBのレコードをすべて削除する
* DBにユーザ"m-takehara"のレコードを挿入する
* URL"http://localhost:8080/users/m-takehara"にボディ<file:jsons/link-zenn.json>で、PUTリクエストを送る
* レスポンスステータスコードが"200"である
* DB"demo_db"の"demo_schema"スキーマの"accounts"テーブルの、"user_id"を"'m-takehara'"で取得した一意の"account_id"が文字列の"m_takehara"である
* DB"demo_db"の"demo_schema"スキーマの"accounts"テーブルの、"user_id"を"'m-takehara'"で取得した一意の"service"が文字列の"zenn"である

## ユーザに紐付いているアカウントの全記事を取得できる
tags: unimplemented
* DBのレコードをすべて削除する
* DBにユーザ"m-takehara"のレコードを挿入する
* DBのユーザ"m-takehara"に"qiita"のアカウント"m-takehara"を紐付ける
* DBのユーザ"m-takehara"に"zenn"のアカウント"m_takehara"を紐付ける
* URL"http://localhost:8080/articles?userId=m-takehara"にGETリクエストを送る
* レスポンスステータスコードが"200"である
* レスポンスのJSONの"$.articles[0].title"が文字列の"え！！社内ネットワーク内だけで Code with me を！？"である
* レスポンスのJSONの"$.articles[0].url"が文字列の"https://qiita.com/m-takehara/items/8e0d62bf3edf29efd5cd"である
* レスポンスのJSONの"$.articles[1].title"が文字列の"え！！複数の JVM プロジェクトで同時に Code with me を！？"である
* レスポンスのJSONの"$.articles[1].url"が文字列の"https://qiita.com/m-takehara/items/df4df24d7de3f9c40f79"である
* レスポンスのJSONの"$.articles[2].title"が文字列の"型を活用したプログラミング"である
* レスポンスのJSONの"$.articles[2].url"が文字列の"https://zenn.dev/m_takehara/articles/351624df648ce9cb0ed0"である
