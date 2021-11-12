package com.example.demo.controller

import com.example.demo.model.*
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.*
import java.sql.Connection

class StudentListController: Controller() {
    val selectedPerson = StudentDetailModel()
    val students : ObservableList<StudentModel> by lazy {
        transaction {
            Student.all().with(Student::subjects).map {
                StudentModel().apply {
                    item = it
                }
            }.observable()
        }
    }


    fun deleteUser(model: StudentModel){
        transaction {
            model.item.delete()
        }
        students.remove(model)
    }

    fun addUser(name: String, type: String){
        transaction {
            val student = Student.new {
                this.name = name
                this.type = type
            }
            students.add(
                StudentModel().apply {
                    item = student
                }
            )
        }
    }

    fun commitDirty(modelDirtyMappings: Sequence<Map.Entry<StudentModel,TableColumnDirtyState<StudentModel>>>){
        transaction {
            modelDirtyMappings.filter { it.value.isDirty }.forEach {
                it.key.commit()
                it.value.commit()
            }
        }
    }

}