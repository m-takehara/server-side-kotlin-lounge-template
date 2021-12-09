package me.takehara.rest

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.Koin
import org.koin.logger.SLF4JLogger

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(Koin) {
        SLF4JLogger()
    }

    install(ContentNegotiation) {
        gson {
        }
    }

    routing {
        route("/ping") {
            get {
                call.respond("pong\n")
            }
        }
    }
}
