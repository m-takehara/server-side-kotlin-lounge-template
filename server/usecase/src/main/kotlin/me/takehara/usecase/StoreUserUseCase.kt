package me.takehara.usecase

import me.takehara.domain.UserId
import me.takehara.port.UserPort

class StoreUserUseCase(private val userPort: UserPort) {
    fun storeUser(id: UserId) {
        userPort.store(id)
    }
}
