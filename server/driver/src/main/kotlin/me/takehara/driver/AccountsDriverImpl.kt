package me.takehara.driver

import me.takehara.gateway.interfaces.AccountModel
import me.takehara.gateway.interfaces.AccountsDriver
import org.jetbrains.exposed.sql.*
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

    override fun findAllByUserId(userId: String): List<AccountModel> {
        return transaction {
            Accounts.select { Accounts.userId eq userId }
                .map {
                    val id = it[Accounts.userId]
                    val accountId = it[Accounts.accountId]
                    val service = it[Accounts.service]
                    AccountModel(id, accountId, service)
                }
        }
    }
}

object Accounts : Table("demo_schema.accounts") {
    val userId: Column<String> = text("user_id") references Users.id
    val accountId: Column<String> = text("account_id")
    val service: Column<String> = text("service")
}
