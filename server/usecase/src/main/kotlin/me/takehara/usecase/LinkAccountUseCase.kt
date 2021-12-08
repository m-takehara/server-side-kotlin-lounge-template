package me.takehara.usecase

import me.takehara.domain.AccountId
import me.takehara.domain.Service
import me.takehara.domain.UserId
import me.takehara.port.AccountPort

class LinkAccountUseCase(private val accountPort: AccountPort) {
    fun link(userId: UserId, accountId: AccountId, service: Service) {
        accountPort.store(userId, accountId, service)
    }
}
