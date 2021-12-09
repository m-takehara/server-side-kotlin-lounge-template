package me.takehara.gateway

import me.takehara.domain.*
import me.takehara.gateway.interfaces.AccountModel
import me.takehara.gateway.interfaces.AccountsDriver
import me.takehara.port.AccountPort

class AccountGateway(private val accountsDriver: AccountsDriver) : AccountPort {
    override fun store(userId: UserId, accountId: AccountId, service: Service) {
        val model = AccountModel(userId.value, accountId.value, service.value)
        accountsDriver.store(model)
    }

    override fun findAllByUserId(userId: UserId): Accounts {
        return accountsDriver.findAllByUserId(userId.value)
            .map {
                val accountId = AccountId(it.accountId)
                val service = Service.from(it.service)
                Account(accountId, service)
            }.let(::Accounts)
    }
}
