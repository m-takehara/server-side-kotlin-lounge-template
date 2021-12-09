package me.takehara.gateway

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import me.takehara.domain.*
import me.takehara.gateway.interfaces.*

class ArticleGatewayTest : StringSpec({
    val accountsDriver = mockk<AccountsDriver>()
    val qiitaAccountDriver = mockk<QiitaAccountDriver>()
    val zennAccountDriver = mockk<ZennAccountDriver>()
    val target = ArticleGateway(accountsDriver, qiitaAccountDriver, zennAccountDriver)

    "QiitaとZennからユーザの記事をすべて取得できる" {
        val userId = UserId("userId")
        val qiitaId = AccountId("qiita")
        val qiitaArticles = listOf(
            QiitaArticleModel("title1", "url1"),
            QiitaArticleModel("title2", "url2")
        )
        val zennId = AccountId("zenn")
        val zennArticles = listOf(
            ZennArticleModel("title3", "url3")
        )
        val accountModels = listOf(
            AccountModel(userId.value, qiitaId.value, "qiita"),
            AccountModel(userId.value, zennId.value, "zenn")
        )

        val articles = Articles(listOf(
            Article(Title("title1"), Url("url1"), Service.QIITA),
            Article(Title("title2"), Url("url2"), Service.QIITA),
            Article(Title("title3"), Url("url3"), Service.ZENN),
        ))
        every { qiitaAccountDriver.findArticlesByAccountId(qiitaId.value) } returns qiitaArticles
        every { zennAccountDriver.findArticlesByAccountId(zennId.value) } returns zennArticles
        every { accountsDriver.findAllByUserId(userId.value) } returns accountModels

        target.findAll(userId) shouldBe articles
    }
})
