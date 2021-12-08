package me.takehara.rest

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import me.takehara.domain.AccountId
import me.takehara.domain.Service
import me.takehara.domain.UserId
import me.takehara.usecase.LinkAccountUseCase
import me.takehara.usecase.StoreUserUseCase

class UserResource(
    private val storeUserUseCase: StoreUserUseCase,
    private val linkAccountUseCase: LinkAccountUseCase
) {
    suspend fun store(call: ApplicationCall) {
        val request = call.receive<CreateUserRequest>()
        val id = UserId(request.id)
        storeUserUseCase.storeUser(id)
        val response = CreateUserResponse(id.value)
        call.respond(HttpStatusCode.Created, response)
    }

    suspend fun linkAccount(call: ApplicationCall) {
        val userId = call.parameters["userId"]!!.toString().let(::UserId)
        val request = call.receive<LinkAccountRequest>()
        val accountId = request.accountId.let(::AccountId)
        val service = Service.from(request.service)
        linkAccountUseCase.link(userId, accountId, service)
        call.respond(HttpStatusCode.OK)
    }
}

data class CreateUserRequest(val id: String)
data class CreateUserResponse(val id: String)

data class LinkAccountRequest(val service: String, val accountId: String)
