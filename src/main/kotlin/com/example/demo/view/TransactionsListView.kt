package com.example.demo.view

import com.example.demo.controller.TransactionListController
import com.example.demo.model.SubjectModel
import com.example.demo.model.Transaction
import com.example.demo.model.TransactionModel
import javafx.collections.ObservableList
import tornadofx.*

class TransactionsListView: View("Transaction List"){
    val transactionListController: TransactionListController by inject()
    var transactionsTable: TableViewEditModel<TransactionModel> by singleAssign()
    var transactions : ObservableList<TransactionModel> by singleAssign()

    override val root = borderpane {
        transactions = transactionListController.transactions
        center {
            tableview<TransactionModel> {
                transactionsTable = editModel
                items = transactions

                column("Reason", TransactionModel::reason)
                column("Timestamp", TransactionModel::timestamp)
                column("Amount", TransactionModel::amount)
            }
        }
    }
}