package me.takehara.driver

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import me.takehara.gateway.interfaces.*
import org.w3c.dom.Element
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.xml.parsers.DocumentBuilderFactory

class QiitaAccountDriverImpl : QiitaAccountDriver {
    override fun findAccountById(id: String): QiitaAccountModel {
        val (request, response, result) = "https://qiita.com/api/v2/users/${id}"
            .httpGet()
            .response()

        if (result is Result.Failure) throw QiitaAccountNotFoundException(id)

        return (result as Result.Success).value
            .let(::String)
            .let { Gson().fromJson(it, QiitaAccountResponse::class.java) }
            .let { QiitaAccountModel(it.id) }
    }

    override fun findArticlesByAccountId(id: String): List<QiitaArticleModel> {
        val url = "https://qiita.com/$id/feed"
        val (request, response, result) = url.httpGet().response()
        val rssFeed = when (result) {
            is Result.Success -> result.get().let(::String)
            is Result.Failure -> throw ZennAccountNotFoundException(id)
        }

        val elements = DocumentBuilderFactory.newDefaultInstance()
            .newDocumentBuilder()
            .parse(rssFeed.byteInputStream())
            .also { it.documentElement.normalize() }
            .getElementsByTagName("entry")
        return (0 until elements.length).map { index ->
            val item = elements.item(index) as Element
            val title = item.getElementsByTagName("title").item(0).textContent
            val url = item.getElementsByTagName("link").item(0).attributes.getNamedItem("href").textContent
            QiitaArticleModel(title, url)
        }
    }
}

data class QiitaAccountResponse(val id: String)
