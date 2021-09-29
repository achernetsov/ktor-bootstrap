package tech.achernetsov.lab

import tech.achernetsov.lab.plugins.configureDependencyInjection
import tech.achernetsov.lab.plugins.configureRoutingAndViews
import tech.achernetsov.lab.plugins.configureSecurity
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost") {
        configureDependencyInjection()
        configureSecurity()
        configureRoutingAndViews()
    }.start(wait = true)
}
