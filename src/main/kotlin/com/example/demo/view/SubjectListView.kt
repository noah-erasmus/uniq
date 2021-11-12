package com.example.demo.view

import com.example.demo.controller.SubjectListController
import com.example.demo.model.SubjectModel
import javafx.collections.ObservableList
import tornadofx.*

class SubjectListView: View() {
    val subjectListController : SubjectListController by inject()
    var subjectTable: TableViewEditModel<SubjectModel> by singleAssign()
    var subjects : ObservableList<SubjectModel> by singleAssign()

//    init {
//        subjectListController.setUp()
//    }

    override val root = borderpane {
        subjects = subjectListController.subjects
        center{
            tableview<SubjectModel> {
                subjectTable = editModel
                items = subjects

                column("Name", SubjectModel::name)
                column("Code", SubjectModel::code)
                column("Lecturer", SubjectModel::lecturer)
                column("Hours", SubjectModel::hours)
                column("Credits", SubjectModel::credits)
                column("Price", SubjectModel::price)

                columnResizePolicy = SmartResize.POLICY

            }
        }
        bottom{
            hbox {
                button("Delete subject"){
                    action{
                        val model = subjectTable.tableView.selectedItem
                        when(model){
                            null -> return@action
                            else -> subjectListController.deleteSubject(model)
                        }
                    }
                }
                button("Add Subject ") {
                    action{
                        find<AddSubjectView>().openModal(block=true)
                    }
                }
                button("Commit changes"){
                    action{
                        subjectListController.commitDirty(subjectTable.items.asSequence())
                    }
                }
                button("Rollback changes"){
                    action {
                        subjectTable.rollback()
                    }
                }
            }

        }
    }
}