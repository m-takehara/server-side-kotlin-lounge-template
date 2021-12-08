package me.takehara

import com.thoughtworks.gauge.Step
import com.uzabase.playtest.db.Database
import com.uzabase.playtest.db.Entity

class DatabaseStep {
    private val database = Database(
        Config.getDriverClass(),
        Config.getJdbcUrl(),
        Config.getUsername(),
        Config.getPassword(),
        Config.getSchema()
    )

    @Step("DBのレコードをすべて削除する")
    fun deleteAllRecords() {
        database.truncate("users", "accounts")
    }

    @Step("DBにユーザ<userId>のレコードを挿入する")
    fun insertUser(userId: String) {
        val entity = UserEntity(userId)
        database.insert(entity)
    }

    @Step("DBのユーザ<userId>に<service>のアカウント<accountId>を紐付ける")
    fun insertAccount(userId: String, service: String, accountId: String) {
        val entity = AccountEntity(userId, accountId, service)
        database.insert(entity)
    }
}

data class UserEntity(val id: String): Entity("users")
data class AccountEntity(val user_id: String, val account_id: String, val service: String): Entity("accounts")
