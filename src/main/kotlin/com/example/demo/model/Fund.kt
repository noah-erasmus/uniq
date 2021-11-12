package com.example.demo.model

import com.example.demo.model.Staff.Companion.referrersOn
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import tornadofx.*

object Transactions: IntIdTable(){
    val amount = integer("amount")
    val reason = varchar("type", 255)
    val timestamp = datetime("timestamp")
}

class Transaction(id: EntityID<Int>): IntEntity(id){
    companion object : IntEntityClass<Transaction>(Transactions)

    var reason by Transactions.reason
    var amount by Transactions.amount
    var timestamp by Transactions.timestamp

    override fun toString(): String {
        return "Transaction(reason=\"$reason\", amount=\"$amount\", timestamp=\"$timestamp\")"
    }
}

class TransactionModel(): ItemViewModel<Transaction>(){
    val reason = bind(Transaction::reason)
    val amount = bind(Transaction::amount)
    val timestamp = bind(Transaction::timestamp)
}

class TransactionDetailModel(): ItemViewModel<Transaction>(){
    val reason = bind(Transaction::reason)
    val amount = bind(Transaction::amount)
    val timestamp = bind(Transaction::timestamp)
}