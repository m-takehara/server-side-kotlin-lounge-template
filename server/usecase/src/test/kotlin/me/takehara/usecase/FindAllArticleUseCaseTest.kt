package me.takehara.usecase

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import me.takehara.domain.Articles
import me.takehara.domain.UserId
import me.takehara.port.ArticlePort

class FindAllArticleUseCaseTest : StringSpec({
    val articlePort = mockk<ArticlePort>()
    val target = FindAllArticleUseCase(articlePort)

    "ユーザに紐づく記事をすべて取得できる" {
        val userId = mockk<UserId>()
        val articles = mockk<Articles>()
        every { articlePort.findAll(userId) } returns articles

        target.find(userId) shouldBe articles
    }
})
