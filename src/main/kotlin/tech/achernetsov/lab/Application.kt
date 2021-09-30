package tech.achernetsov.lab

import io.ktor.application.*
import tech.achernetsov.lab.plugins.configureDependencyInjection
import tech.achernetsov.lab.plugins.configureRoutingAndViews
import tech.achernetsov.lab.plugins.configureSecurity

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    configureDependencyInjection()
    configureSecurity()
    configureRoutingAndViews()
}
