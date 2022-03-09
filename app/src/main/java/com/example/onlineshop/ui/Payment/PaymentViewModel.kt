package com.example.onlineshop.ui.Payment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.onlineshop.data.entity.order.CustomerOrder
import com.example.onlineshop.data.entity.order.LineItem
import com.example.onlineshop.data.entity.order.Order
import com.example.onlineshop.data.entity.order.Orders
import com.example.onlineshop.data.entity.orderGet.GetOrders
import com.example.onlineshop.repository.IRepository


class PaymentViewModel(val repositoryImpl: IRepository, application: Application):AndroidViewModel(application) {
    fun createOrderInPayment(order: GetOrders.Order) {
        val customerOrder = CustomerOrder(order.customer!!.id)
        var lineItems: MutableList<LineItem> = arrayListOf()
        for (i in order.line_items!!.indices) {
            lineItems.add(
                LineItem(
                    order.line_items[i]!!.quantity,
                    order.line_items[i]!!.variant_id
                )
            )
        }
        val ord = Order(customerOrder, "paid", lineItems, "card", order.discount_codes)
        val orders = Orders(ord)
        repositoryImpl.createOrder(orders)
    }

    fun cancelOrder(orderId: Long){
        repositoryImpl.deleteOrder(orderId)
    }

}