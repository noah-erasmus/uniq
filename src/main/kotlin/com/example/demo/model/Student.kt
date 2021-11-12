package com.example.demo.model

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import tornadofx.*
import java.sql.Connection

object Students: IntIdTable(){
    val name = varchar("name", 255)
    val type = varchar("type", 255)
}

class Student(id: EntityID<Int>): IntEntity(id){
    companion object : IntEntityClass<Student>(Students)

    var name by Students.name
    var type by Students.type
    var subjects by Subject via StudentsSubjectsTable

    override fun toString(): String {
        return "Student(name=\"$name\", type=\"$type\", subjects=\"$subjects\")"
    }
}

class StudentModel(): ItemViewModel<Student>() {
    val name = bind(Student::name)
    val type = bind(Student::type)
    val subjects = bind(Student::subjects)
}

class StudentDetailModel(): ItemViewModel<Student>() {
    val name = bind(Student::name)
    val type = bind(Student::type)
    val subjects = bind(Student::subjects)
}

object StudentsSubjectsTable : IntIdTable(){
    val subject = reference("subjects", Subjects)
    val student = reference("student", Students)
}

//fun setUp(){
//    Database.connect("jdbc:sqlite:file:data.sqlite", driver="org.sqlite.JDBC")
//    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
//
//
//}