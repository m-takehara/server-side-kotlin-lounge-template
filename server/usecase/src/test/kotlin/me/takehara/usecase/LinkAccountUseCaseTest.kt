package me.takehara.usecase

import io.kotest.core.spec.style.StringSpec
import io.mockk.*
import me.takehara.domain.AccountId
import me.takehara.domain.Service
import me.takehara.domain.UserId
import me.takehara.port.AccountPort

class LinkAccountUseCaseTest : StringSpec({
    val accountPort = mockk<AccountPort>()
    val target = LinkAccountUseCase(accountPort)

    "ユーザにアカウントを紐付けることができる" {
        val userId = mockk<UserId>()
        val accountId = mockk<AccountId>()
        val service = mockk<Service>()
        every { accountPort.store(userId, accountId, service) } just runs

        target.link(userId, accountId, service)

        verify { accountPort.store(userId, accountId, service) }
    }
})
