package com.example.demo.view

import com.example.demo.app.Styles
import com.example.demo.app.initDB
import javafx.collections.FXCollections
import tornadofx.*

class MainView : View("Hello TornadoFX") {
    init {
        initDB()
    }
    override val root = hbox {
        tabpane {
            tab ("Students") {
                hbox {
                    add<StudentListView>()
                    add<StudentDetailsView>()
                }
            }
            tab("Staff"){
                hbox {
                    add<StaffListView>()
                }
            }
        }
        tabpane {
            tab("Subjects") {
                add<SubjectListView>()
            }
            tab("Transactions") {
                add<TransactionsListView>()
            }
        }
    }

}