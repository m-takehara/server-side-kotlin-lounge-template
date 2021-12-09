package me.takehara.domain

data class Title(val value: String)
data class Url(val value: String)

data class Article(val title: Title, val url: Url, val service: Service)
data class Articles(val list: List<Article>) {
    operator fun plus(other: Articles): Articles {
        return Articles(list + other.list)
    }
}
