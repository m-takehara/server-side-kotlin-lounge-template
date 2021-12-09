package me.takehara.usecase

import me.takehara.domain.Articles
import me.takehara.domain.UserId
import me.takehara.port.ArticlePort

class FindAllArticleUseCase(private val articlePort: ArticlePort) {
    fun find(userId: UserId): Articles {
        return articlePort.findAll(userId)
    }
}
