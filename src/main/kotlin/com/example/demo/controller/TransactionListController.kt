package com.example.demo.controller

import com.example.demo.model.*
import javafx.collections.ObservableList
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.*

class TransactionListController: Controller() {
    init {
        println("HI")
    }
    val transactions : ObservableList<TransactionModel> by lazy {
        transaction {
            Transaction.all().map {
                TransactionModel().apply {
                    item = it
                }
            }.observable()
        }
    }
}