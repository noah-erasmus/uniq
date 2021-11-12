package com.example.demo.controller

import com.example.demo.model.*
import javafx.collections.ObservableList
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.*
import java.sql.Connection

class SubjectListController: Controller() {
    val subjects: ObservableList<SubjectModel> by lazy {
        transaction {
            Subject.all().map{
                SubjectModel().apply {
                    item = it
                }
            }.observable()
        }
    }

    init {
        Database.connect("jdbc:sqlite:file:data.sqlite", driver="org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
    }

    fun commitDirty(modelDirtyMappings: Sequence<Map.Entry<SubjectModel,TableColumnDirtyState<SubjectModel>>>){
        transaction {
            modelDirtyMappings.filter { it.value.isDirty }.forEach {
                it.key.commit()
                it.value.commit()
            }
        }
    }

    fun deleteSubject(model: SubjectModel){
        transaction {
            model.item.delete()
        }
        subjects.remove(model)
    }

    fun addSubject(name: String, code: String, lecturer: Int, hours: Int, credits: Int, price: Int){
        transaction {
            val subject = Subject.new {
                this.name = name
                this.code = code
                this.lecturer = Staff.findById(lecturer)!!
                this.hours = hours
                this.credits = credits
                this.price = price
            }
            subjects.add(
                SubjectModel().apply {
                    item = subject
                }
            )
        }
    }


}