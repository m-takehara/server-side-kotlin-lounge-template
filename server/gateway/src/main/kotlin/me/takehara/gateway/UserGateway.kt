package me.takehara.gateway

import me.takehara.domain.UserId
import me.takehara.gateway.interfaces.UserModel
import me.takehara.gateway.interfaces.UsersDriver
import me.takehara.port.UserPort

class UserGateway(private val usersDriver: UsersDriver) : UserPort {
    override fun store(id: UserId) {
        val model = UserModel(id.value)
        usersDriver.store(model)
    }
}
