package com.example.demo.app

import com.example.demo.model.*
import com.example.demo.model.Transaction
import com.example.demo.model.Transactions.amount
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.sql.Connection

fun initDB(){
    Database.connect("jdbc:sqlite:file:data.sqlite", driver="org.sqlite.JDBC")
    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
    var tables = setOf(Students, Subjects, Staffs, StudentsSubjectsTable, Transactions)



    transaction {
        tables.forEach {
            SchemaUtils.drop(it)
            SchemaUtils.create(it)
        }
        val noah = Student.new{
            name = "Noah"
            type = "Degree"
        }

        val dylan = Student.new{
            name = "Bryan"
            type = "Diploma"
        }

        val helen = Staff.new{
            name="Helen"
            type="Academic"
        }

        val christof = Staff.new{
            name="Christof"
            type="Academic"
        }

        Transaction.new{
            amount=1000000
            reason="Float"
            timestamp= DateTime.now()
        }

        Transaction.new{
            amount=1000000
            reason="New hires"
            timestamp= DateTime.now()
        }

        Subject.new{
            name="Visual Culture"
            code="300"
            lecturer=helen
            hours=4
            credits=60
            price=2000
        }

        val idv = Subject.new{
            name="Interactive Development"
            code="303"
            lecturer=christof
            hours=4
            credits=60
            price=2000
        }

        idv.students = SizedCollection(listOf(noah, dylan))
        println(Transactions.slice(Transactions.amount.sum()).selectAll().first())
    }



}