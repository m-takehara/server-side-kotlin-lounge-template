package me.takehara.rest

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*
import me.takehara.driver.AccountsDriverImpl
import me.takehara.driver.QiitaAccountDriverImpl
import me.takehara.driver.UsersDriverImpl
import me.takehara.driver.ZennAccountDriverImpl
import me.takehara.gateway.AccountGateway
import me.takehara.gateway.ArticleGateway
import me.takehara.gateway.UserGateway
import me.takehara.gateway.interfaces.AccountsDriver
import me.takehara.gateway.interfaces.QiitaAccountDriver
import me.takehara.gateway.interfaces.UsersDriver
import me.takehara.gateway.interfaces.ZennAccountDriver
import me.takehara.port.AccountPort
import me.takehara.port.ArticlePort
import me.takehara.port.UserPort
import me.takehara.usecase.FindAllArticleUseCase
import me.takehara.usecase.LinkAccountUseCase
import me.takehara.usecase.StoreUserUseCase
import org.jetbrains.exposed.sql.Database
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.koin.logger.SLF4JLogger

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(Koin) {
        SLF4JLogger()
        modules(driverModules, gatewayModules, useCaseModules, restModules, database(environment))
    }

    install(ContentNegotiation) {
        gson {
        }
    }

    routing {
        val userResource by inject<UserResource>()
        route("/users") {

            post {
                userResource.store(call)
            }
            put("/{userId}") {
                userResource.linkAccount(call)
            }
        }
        route("/articles") {
            get {
                userResource.findAllArticles(call)
            }
        }
    }
}

val driverModules = module {
    single<AccountsDriver> { AccountsDriverImpl(get()) }
    single<UsersDriver> { UsersDriverImpl(get()) }
    single<QiitaAccountDriver> { QiitaAccountDriverImpl() }
    single<ZennAccountDriver> { ZennAccountDriverImpl() }
}

val gatewayModules = module {
    single<AccountPort> { AccountGateway(get()) }
    single<UserPort> { UserGateway(get()) }
    single<ArticlePort> { ArticleGateway(get(), get(), get()) }
}

val useCaseModules = module {
    single { LinkAccountUseCase(get()) }
    single { StoreUserUseCase(get()) }
    single { FindAllArticleUseCase(get()) }
}

val restModules = module {
    single { UserResource(get(), get(), get()) }
}

private fun database(environment: ApplicationEnvironment): Module {
    val jdbcUrl = environment.config.property("ktor.database.jdbcUrl").getString()
    val driverClass = environment.config.property("ktor.database.driverClass").getString()
    val username = environment.config.property("ktor.database.username").getString()
    val password = environment.config.property("ktor.database.password").getString()
    val database = Database.connect(jdbcUrl, driverClass, username, password)
    return module { single { database } }
}
