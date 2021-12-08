package me.takehara.gateway

import me.takehara.domain.AccountId
import me.takehara.domain.Service
import me.takehara.domain.UserId
import me.takehara.gateway.interfaces.AccountModel
import me.takehara.gateway.interfaces.AccountsDriver
import me.takehara.port.AccountPort

class AccountGateway(private val accountsDriver: AccountsDriver) : AccountPort {
    override fun store(userId: UserId, accountId: AccountId, service: Service) {
        val model = AccountModel(userId.value, accountId.value, service.value)
        accountsDriver.store(model)
    }
}
