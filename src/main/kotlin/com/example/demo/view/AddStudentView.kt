package com.example.demo.view

import com.example.demo.controller.StudentListController
import tornadofx.*
import javafx.scene.control.TextField

class AddStudentView: Fragment("Add Student") {
    val studentListController : StudentListController by inject()
    var nameField : TextField by singleAssign()
    var typeField : TextField by singleAssign()

    override val root = form{
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
        button("Add Student") {
            action{
                studentListController.addUser(nameField.text, typeField.text)
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