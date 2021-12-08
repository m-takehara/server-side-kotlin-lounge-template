package me.takehara.gateway.interfaces

interface ZennAccountDriver {
    fun findAccountById(id: String): ZennAccountModel
    fun findArticlesByAccountId(id: String): List<ZennArticleModel>
}

data class ZennAccountModel(val id: String)
class ZennAccountNotFoundException(id: String) : RuntimeException("Zenn Account $id not found.")

data class ZennArticleModel(val title: String, val url: String)
