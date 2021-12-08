package me.takehara.gateway.interfaces

interface AccountsDriver {
    fun store(account: AccountModel)
}

data class AccountModel(val userId: String, val accountId: String, val service: String)
