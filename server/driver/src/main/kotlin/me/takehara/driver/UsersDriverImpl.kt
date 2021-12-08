package me.takehara.driver

import me.takehara.gateway.interfaces.UserModel
import me.takehara.gateway.interfaces.UsersDriver
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class UsersDriverImpl(private val database: Database) : UsersDriver {
    override fun store(user: UserModel) {
        transaction {
            Users.insert {
                it[id] = user.id
            }
        }
    }

    override fun findById(id: String): UserModel {
        return transaction {
            val result = Users.select { Users.id eq id }.single()
            val userId = result[Users.id]
            UserModel(userId)
        }
    }
}

object Users : Table("demo_schema.users") {
    val id: Column<String> = varchar("id", 128)
}
