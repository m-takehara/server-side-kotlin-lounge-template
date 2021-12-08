package me.takehara.gateway.interfaces

interface QiitaAccountDriver {
    fun findAccountById(id: String): QiitaAccountModel
    fun findArticlesByAccountId(id: String): List<QiitaArticleModel>
}

data class QiitaAccountModel(val id: String)
class QiitaAccountNotFoundException(id: String) : RuntimeException("Qiita Account $id not found.")

data class QiitaArticleModel(val title: String, val url: String)
