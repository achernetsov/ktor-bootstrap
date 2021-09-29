package tech.achernetsov.lab.service

import tech.achernetsov.lab.model.Order

interface OrdersService {
    fun findAll(userId: String): List<Order>
}

class OrdersServiceImpl: OrdersService {
    override fun findAll(userId: String) : List<Order> {
        return listOf(Order("order1"), Order("order2"))
    }
}
