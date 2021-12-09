package me.takehara.domain

data class AccountId(val value: String)

data class Account(val id: AccountId, val service: Service)
data class Accounts(val list: List<Account>)
