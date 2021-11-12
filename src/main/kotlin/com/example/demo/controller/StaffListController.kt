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

class StaffListController: Controller() {
    val selectedPerson = StaffDetailModel()
    val staffs : ObservableList<StaffModel> by lazy {
        transaction {
            Staff.all().map {
                StaffModel().apply {
                    item = it
                }
            }.observable()
        }
    }

    init{
        Database.connect("jdbc:sqlite:file:data.sqlite", driver="org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
    }

    fun deleteUser(model: StaffModel){
        transaction {
            model.item.delete()
        }
        staffs.remove(model)
    }

    fun addUser(name: String, type: String){
        transaction {
            val staff = Staff.new {
                this.name = name
                this.type = type
            }
            staffs.add(
                StaffModel().apply {
                    item = staff
                }
            )
        }
    }

    fun commitDirty(modelDirtyMappings: Sequence<Map.Entry<StaffModel,TableColumnDirtyState<StaffModel>>>){
        transaction {
            modelDirtyMappings.filter { it.value.isDirty }.forEach {
                it.key.commit()
                it.value.commit()
            }
        }
    }


}