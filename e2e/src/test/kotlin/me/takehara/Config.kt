package me.takehara

import java.util.*

object Config {
    private val properties: Properties = Properties().apply {
        val resource = Config.javaClass.getResourceAsStream("/e2e.properties")
        load(resource)
    }

    fun getJdbcUrl(): String {
        return properties["db.jdbcUrl"].toString()
    }

    fun getDriverClass(): String {
        return properties["db.driverClass"].toString()
    }

    fun getUsername(): String {
        return properties["db.username"].toString()
    }

    fun getPassword(): String {
        return properties["db.password"].toString()
    }

    fun getSchema(): String {
        return properties["db.schema"].toString()
    }
}
