package me.takehara.port

import me.takehara.domain.UserId

interface UserPort {
    fun store(id: UserId)
}
