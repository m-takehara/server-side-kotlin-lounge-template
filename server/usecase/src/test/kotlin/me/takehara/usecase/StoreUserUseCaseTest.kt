package me.takehara.usecase

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import me.takehara.domain.UserId
import me.takehara.port.UserPort

class StoreUserUseCaseTest : StringSpec({
    val userPort = mockk<UserPort>()
    val target = StoreUserUseCase(userPort)

    "ユーザを保存できる" {
        val id = mockk<UserId>()
        every { userPort.store(id) } just runs

        target.storeUser(id)

        verify { userPort.store(id) }
    }
})
