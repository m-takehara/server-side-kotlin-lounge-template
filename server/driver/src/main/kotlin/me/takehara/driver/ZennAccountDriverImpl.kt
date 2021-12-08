package me.takehara.driver

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import me.takehara.gateway.interfaces.ZennAccountDriver
import me.takehara.gateway.interfaces.ZennAccountModel
import me.takehara.gateway.interfaces.ZennAccountNotFoundException
import me.takehara.gateway.interfaces.ZennArticleModel
import org.w3c.dom.Element
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.xml.parsers.DocumentBuilderFactory

class ZennAccountDriverImpl : ZennAccountDriver {
    override fun findAccountById(id: String): ZennAccountModel {
        val (request, response, result) = "https://zenn.dev/$id"
            .let { URLEncoder.encode(it, StandardCharsets.UTF_8) }
            .httpGet()
            .response()
        return if (response.statusCode == 200) ZennAccountModel(id)
        else throw ZennAccountNotFoundException(id)
    }

    override fun findArticlesByAccountId(id: String): List<ZennArticleModel> {
        val (request, response, result) = "https://zenn.dev/$id/feed"
            .let { URLEncoder.encode(it, StandardCharsets.UTF_8) }
            .httpGet()
            .response()
        val rssFeed = when (result) {
            is Result.Success -> result.get().let(::String)
            is Result.Failure -> throw ZennAccountNotFoundException(id)
        }

        val elements = DocumentBuilderFactory.newDefaultInstance()
            .newDocumentBuilder()
            .parse(rssFeed.byteInputStream())
            .also { it.documentElement.normalize() }
            .getElementsByTagName("item")
        return (0 until elements.length).map { index ->
            val item = elements.item(index) as Element
            val title = item.getElementsByTagName("title").item(0).textContent
            val url = item.getElementsByTagName("link").item(0).textContent
            ZennArticleModel(title, url)
        }
    }
}
