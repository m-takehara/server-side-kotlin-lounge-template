package me.takehara.port

import me.takehara.domain.Articles
import me.takehara.domain.UserId

interface ArticlePort {
    fun findAll(userId: UserId): Articles
}
