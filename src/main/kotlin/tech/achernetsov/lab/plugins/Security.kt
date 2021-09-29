package tech.achernetsov.lab.plugins

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.sessions.*
import java.io.File

data class UserSession(val userId: String, val count: Int) : Principal

fun Application.configureSecurity() {
    install(Sessions) {
        cookie<UserSession>("user_session", storage = directorySessionStorage(File(".app/sessions"))) {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 60
        }
    }
    install(Authentication) {
        form("auth-form") {
            userParamName = "username"
            passwordParamName = "password"
            validate { credentials ->
                if (credentials.name == "user" && credentials.password == "123") {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
        session<UserSession>("auth-session") {
            validate { session ->
                if (session.userId.startsWith("user")) {
                    session
                } else {
                    null
                }
            }
            challenge {
                call.respondRedirect("/login")
            }
        }
    }
}
