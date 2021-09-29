package tech.achernetsov.lab.plugins

import tech.achernetsov.lab.service.OrdersService
import tech.achernetsov.lab.service.OrdersServiceImpl
import io.ktor.application.*
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.dsl.single
import org.koin.ktor.ext.Koin

fun Application.configureDependencyInjection() {
    val ordersModule = module {
        single<OrdersServiceImpl>(createOnStart = true) bind OrdersService::class
    }

    install(Koin) {
        modules(ordersModule)
    }
}
