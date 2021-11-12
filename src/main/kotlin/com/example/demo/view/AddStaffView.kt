package com.example.demo.view

import com.example.demo.controller.StaffListController
import com.example.demo.controller.StudentListController
import javafx.scene.control.TextField
import tornadofx.*

class AddStaffView: Fragment("Add Staff") {
    val staffListController : StaffListController by inject()
    var nameField : TextField by singleAssign()
    var typeField : TextField by singleAssign()

    override val root = form {
        fieldset {
            field("Name:") {
                textfield {
                    nameField = this
                }
            }
            field("Type:") {
                textfield {
                    typeField = this
                }
            }
        }
        button("Add Staff") {
            action{
                staffListController.addUser(nameField.text, typeField.text)
                nameField.text = ""
                typeField.text = ""
                close()
            }
        }
        button("Cancel"){
            action {
                close()
            }
        }
    }
}