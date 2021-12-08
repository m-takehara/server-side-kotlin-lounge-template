package me.takehara.domain

enum class Service(val value: String) {
    QIITA("qiita"),
    ZENN("zenn");

    companion object {
        fun from(key: String): Service {
            return values().firstOrNull { it.value == key } ?: throw InvalidServiceKeyException(key)
        }
    }
}

class InvalidServiceKeyException(val key: String): RuntimeException("Invalid service key: $key")
