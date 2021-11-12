package com.example.demo.model

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import tornadofx.*

object Subjects: IntIdTable(){
    val name = varchar("name", 255).uniqueIndex()
    val code = varchar("code", 255)
    val hours = integer("hours")
    val credits = integer("credits")
    val price = integer("price")
    val lecturerID = reference("lecturer", Staffs)
}

class Subject(id: EntityID<Int>): IntEntity(id){
    companion object : IntEntityClass<Subject>(Subjects)

    var name by Subjects.name
    var code by Subjects.code
    var lecturer by Staff referencedOn Subjects.lecturerID
    var hours by Subjects.hours
    var credits by Subjects.credits
    var price by Subjects.price
    var students by Student via StudentsSubjectsTable

    override fun toString(): String {
        return "Subject(name=\"$name\", code=\"$code\", lecturer=\"$lecturer\", hours=\"$hours\", credits=\"$credits\", price=\"$price\")"
    }
}

class SubjectModel(): ItemViewModel<Subject>(){
    val name = bind(Subject::name)
    val code = bind(Subject::code)
    val lecturer = bind(Subject::lecturer)
    val hours = bind(Subject::hours)
    val credits = bind(Subject::credits)
    val price = bind(Subject::price)
}

class SubjectDetailModel(): ItemViewModel<Subject>(){
    val name = bind(Subject::name)
    val code = bind(Subject::code)
    val lecturer = bind(Subject::lecturer)
    val hours = bind(Subject::hours)
    val credits = bind(Subject::credits)
    val price = bind(Subject::price)
}