package me.takehara.gateway

import me.takehara.domain.*
import me.takehara.gateway.interfaces.AccountsDriver
import me.takehara.gateway.interfaces.QiitaAccountDriver
import me.takehara.gateway.interfaces.ZennAccountDriver
import me.takehara.port.ArticlePort

class ArticleGateway(
    private val accountsDriver: AccountsDriver,
    private val qiitaAccountDriver: QiitaAccountDriver,
    private val zennAccountDriver: ZennAccountDriver
) : ArticlePort {
    override fun findAll(userId: UserId): Articles {
        val accounts = accountsDriver.findAllByUserId(userId.value).map {
            val accountId = it.accountId.let(::AccountId)
            val service = Service.from(it.service)
            Account(accountId, service)
        }

        val qiitaArticles = accounts.find { it.service == Service.QIITA }
            ?.let { qiitaAccountDriver.findArticlesByAccountId(it.id.value) }
            ?.map { Article(Title(it.title), Url(it.url), Service.QIITA) }
            ?.sortedBy { it.title.value }
            ?.let(::Articles) ?: Articles(emptyList())
        val zennArticles = accounts.find { it.service == Service.ZENN }
            ?.let { zennAccountDriver.findArticlesByAccountId(it.id.value) }
            ?.map { Article(Title(it.title), Url(it.url), Service.ZENN) }
            ?.sortedBy { it.title.value }
            ?.let(::Articles) ?: Articles(emptyList())

        return qiitaArticles + zennArticles
    }
}
