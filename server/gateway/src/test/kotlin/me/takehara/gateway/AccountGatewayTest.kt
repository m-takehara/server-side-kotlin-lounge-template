package me.takehara.gateway

import io.kotest.core.spec.style.StringSpec
import io.mockk.*
import me.takehara.domain.AccountId
import me.takehara.domain.Service
import me.takehara.domain.UserId
import me.takehara.gateway.interfaces.AccountModel
import me.takehara.gateway.interfaces.AccountsDriver

class AccountGatewayTest : StringSpec({
    val accountsDriver = mockk<AccountsDriver>()
    val target = AccountGateway(accountsDriver)

    "アカウントの紐付けを保存できる" {
        val userId = mockk<UserId>()
        val accountId = mockk<AccountId>()
        val service = mockk<Service>()

        every { userId.value } returns "userId"
        every { accountId.value } returns "accountId"
        every { service.value } returns "service"
        every { accountsDriver.store(AccountModel("userId", "accountId", "service")) } just runs

        target.store(userId, accountId, service)

        verify { userId.value }
        verify { accountId.value }
        verify { service.value }
        verify { accountsDriver.store(AccountModel("userId", "accountId", "service")) }
    }
})
