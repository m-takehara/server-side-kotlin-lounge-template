package me.takehara.gateway

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import me.takehara.domain.UserId
import me.takehara.gateway.interfaces.UserModel
import me.takehara.gateway.interfaces.UsersDriver

class UserGatewayTest : StringSpec({
    val usersDriver = mockk<UsersDriver>()
    val target = UserGateway(usersDriver)

    "ユーザを保存できる" {
        val id = mockk<UserId>()
        val value = "value"
        val model = UserModel(value)
        every { id.value } returns value
        every { usersDriver.store(model) } just runs

        target.store(id)

        verify { id.value }
        verify { usersDriver.store(model) }
    }
})
