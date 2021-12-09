package me.takehara.port

import me.takehara.domain.AccountId
import me.takehara.domain.Accounts
import me.takehara.domain.Service
import me.takehara.domain.UserId

interface AccountPort {
    fun store(userId: UserId, accountId: AccountId, service: Service)
    fun findAllByUserId(userId: UserId): Accounts
}
