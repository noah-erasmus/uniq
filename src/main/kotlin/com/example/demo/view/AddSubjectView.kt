package com.example.demo.view

import com.example.demo.controller.StudentListController
import com.example.demo.controller.SubjectListController
import javafx.scene.control.TextField
import tornadofx.*

class AddSubjectView: Fragment("Add Subject") {
    val subjectListController : SubjectListController by inject()
    var nameField : TextField by singleAssign()
    var codeField : TextField by singleAssign()
    var lecturerField : TextField by singleAssign()
    var hoursField : TextField by singleAssign()
    var creditsField : TextField by singleAssign()
    var priceField : TextField by singleAssign()

    override val root = form{
        fieldset {
            field("Name:") {
                textfield {
                    nameField = this
                }
            }
            field("Code:") {
                textfield {
                    codeField = this
                }
            }
            field("Lecturer:") {
                textfield {
                    lecturerField = this
                }
            }
            field("Hours per week:") {
                textfield {
                    hoursField = this
                }
            }
            field("Credits:") {
                textfield {
                    creditsField = this
                }
            }
            field("Price:") {
                textfield {
                    priceField = this
                }
            }
        }
        button("Add Subject") {
            action {
                subjectListController.addSubject(nameField.text,codeField.text,lecturerField.text.toInt(),hoursField.text.toInt(),creditsField.text.toInt(),priceField.text.toInt())
                nameField.text = ""
                codeField.text = ""
                lecturerField.text = ""
                hoursField.text = ""
                creditsField.text = ""
                priceField.text = ""
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