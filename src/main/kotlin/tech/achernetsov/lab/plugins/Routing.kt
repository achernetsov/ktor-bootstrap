package tech.achernetsov.lab.plugins

import tech.achernetsov.lab.service.OrdersService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.http.content.*
import io.ktor.routing.get
import io.ktor.thymeleaf.*
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import org.koin.ktor.ext.inject

fun Application.configureRoutingAndViews() {
    val ordersService: OrdersService by inject()

    install(Thymeleaf) {
        setTemplateResolver(ClassLoaderTemplateResolver().apply {
            prefix = "templates/"
            suffix = ".html"
            characterEncoding = "utf-8"
        })
    }

    routing {
        static("assets") {
            resources("templates/assets")
        }
        static("css") {
            resources("templates/css")
        }
        static("js") {
            resources("templates/js")
        }
        static("/static") {
            resources("static")
        }
        get("/") {
            call.respondRedirect("/orders")
        }
        get("/login") {
            call.respond(ThymeleafContent("login", emptyMap()))
        }

        authenticate("auth-form") {
            post("/login") {
                val userName = call.principal<UserIdPrincipal>()?.name.toString()
                call.sessions.set(UserSession(userId = userName, count = 1))
                call.respondRedirect("/orders")
            }
        }

        authenticate("auth-session") {
            get("/orders") {
                val userSession = call.principal<UserSession>()
                val orders = ordersService.findAll(userSession!!.userId)
                call.respond(ThymeleafContent("orders", mapOf("orders" to orders)))
            }
        }

        get("/logout") {
            call.sessions.clear<UserSession>()
            call.respondRedirect("/login")
        }
    }
}

