package com.example.demo.model

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import tornadofx.*

object Staffs: IntIdTable(){
    val name = varchar("name", 255)
    val type = varchar("type", 255)
}

class Staff(id: EntityID<Int>): IntEntity(id){
    companion object : IntEntityClass<Staff>(Staffs)

    var name by Staffs.name
    var type by Staffs.type
    val subjects by Subject referrersOn Subjects.lecturerID

    override fun toString(): String {
        return "Staff(name=\"$name\", type=\"$type\")"
    }
}


class StaffModel(): ItemViewModel<Staff>(){
    val name = bind(Staff::name)
    val type = bind(Staff::type)
}

class StaffDetailModel(): ItemViewModel<Staff>(){
    val name = bind(Staff::name)
    val type = bind(Staff::type)
}