package me.takehara.driver

import me.takehara.gateway.interfaces.AccountModel
import me.takehara.gateway.interfaces.AccountsDriver
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class AccountsDriverImpl(private val database: Database) : AccountsDriver {
    override fun store(account: AccountModel) {
        transaction {
            Accounts.insert {
                it[Accounts.userId] = account.userId
                it[Accounts.accountId] = account.accountId
                it[Accounts.service] = account.service
            }
        }
    }
}

object Accounts : Table("demo_schema.accounts") {
    val userId: Column<String> = text("user_id") references Users.id
    val accountId: Column<String> = text("account_id")
    val service: Column<String> = text("service")
}
