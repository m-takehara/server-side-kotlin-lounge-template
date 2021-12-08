package me.takehara.gateway.interfaces

interface UsersDriver {
    fun store(user: UserModel)
    fun findById(id: String): UserModel
}

data class UserModel(val id: String)
