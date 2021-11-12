package com.example.demo.view

import com.example.demo.controller.StaffListController
import com.example.demo.controller.StudentListController
import com.example.demo.model.StaffDetailModel
import com.example.demo.model.StaffModel
import com.example.demo.model.StudentDetailModel
import com.example.demo.model.StudentModel
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*

class StaffListView: View() {
    val staffListController: StaffListController by inject()
    var staffTable: TableViewEditModel<StaffModel> by singleAssign()
    var staffs : ObservableList<StaffModel> by singleAssign()

//    init {
//        staffListController.setUp()
//    }

    override val root = borderpane {
        staffs = staffListController.staffs
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
                tableview<StaffModel>{
                    staffTable = editModel
                    items = staffs

                    enableCellEditing()
                    enableDirtyTracking()

                    column("Name", StaffModel::name).makeEditable()
                    column("Type", StaffModel::type).makeEditable()
                    staffListController.selectedPerson.rebindOnChange(this){ selectedItem ->
                        item = selectedItem?.item ?: StaffDetailModel().item
                    }
                    columnResizePolicy = SmartResize.POLICY
                }
            }
        }
        bottom{
            hbox{
                button("Delete user"){
                    action{
                        val model = staffTable.tableView.selectedItem
                        when(model){
                            null -> return@action
                            else -> staffListController.deleteUser(model)
                        }
                    }
                }
                button("Add user") {
                    action{
                        find<AddStaffView>().openModal(block=true)
                    }
                }
                button("Commit changes"){
                    action{
                        staffListController.commitDirty(staffTable.items.asSequence())
                    }
                }
                button("Rollback changes"){
                    action {
                        staffTable.rollback()
                    }
                }
            }
        }
    }
}