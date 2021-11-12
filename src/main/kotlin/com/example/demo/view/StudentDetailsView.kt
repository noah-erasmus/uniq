package com.example.demo.view

import com.example.demo.controller.StudentListController
import com.example.demo.model.*
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.*
import java.awt.TextField

class StudentDetailsView: View() {
    val studentListController : StudentListController by inject()
    var subjectTable: TableViewEditModel<SubjectModel> by singleAssign()
    var nameField : TextField by singleAssign()
    var typeField : TextField by singleAssign()

    override val root = form{
        fieldset {
            field("Name:"){
                textfield(studentListController.selectedPerson.name)
            }
            field("Type:"){
                textfield(studentListController.selectedPerson.type)
            }
        }
        button("Save"){
            action{
//                studentModel.commit()
            }
        }
        label { "Subjects" }
        tableview<SubjectModel> {
            subjectTable = editModel
            transaction {
                val tableItems = studentListController.selectedPerson.subjects.getValue()
                if (tableItems !== null){

                    items = FXCollections.observableList(tableItems.toList().map {SubjectModel().apply  {
                        item = it
                    }})


                }
            }


            column("Name", SubjectModel::name)
            column("Code", SubjectModel::code)
            column("Lecturer", SubjectModel::lecturer)
            column("Hours", SubjectModel::hours)
            column("Credits", SubjectModel::credits)
            column("Price", SubjectModel::price)

            columnResizePolicy = SmartResize.POLICY
        }
    }
}