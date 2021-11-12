package com.example.demo.view

import com.example.demo.controller.StudentListController
import com.example.demo.model.Student
import com.example.demo.model.StudentDetailModel
import com.example.demo.model.StudentModel
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.*

class StudentListView: View("Student List") {
    val studentListController: StudentListController by inject()
    var studentTable: TableViewEditModel<StudentModel> by singleAssign()
    var students : ObservableList<StudentModel> by singleAssign()

//    init{
//        studentListController.setUp()
//    }

    override val root = borderpane {
        students = studentListController.students
        top{
            hbox(spacing=10) {
                style{
                    padding = box(0.px, 0.px, 0.px, 0.px)
                }

                val t = textfield("")

                val filterTypes = FXCollections.observableArrayList("All","Name","Type")

                val c = combobox (values = filterTypes)

                button("Filter List"){
//                            setOnAction {
//                                if (c.value !== null){
//
//                                }
//                            }
                }

                button("Clear Filter"){

                }
            }
        }
        center{
            hbox{
                tableview<StudentModel>{
                    studentTable = editModel
                    items = students

                    enableCellEditing()
                    enableDirtyTracking()

                    column("Name", StudentModel::name).makeEditable()
                    column("Type", StudentModel::type).makeEditable()
                    studentListController.selectedPerson.rebindOnChange(this){ selectedItem ->
                        item = selectedItem?.item ?: StudentDetailModel().item
                    }
                    columnResizePolicy = SmartResize.POLICY
                }
            }
        }
        bottom{
            hbox{
                button("Delete user"){
                    action{
                        val model = studentTable.tableView.selectedItem
                        when(model){
                            null -> return@action
                            else -> studentListController.deleteUser(model)
                        }
                    }
                }
                button("Add user") {
                    action{
                        find<AddStudentView>().openModal(block=true)
                    }
                }
                button("Commit changes"){
                    action{
                        studentListController.commitDirty(studentTable.items.asSequence())
                    }
                }
                button("Rollback changes"){
                    action {
                        studentTable.rollback()
                    }
                }
            }
        }
    }
}