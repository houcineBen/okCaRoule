package com.example.okcaroule.commandsFragment.dataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Command_table")
data class ElementsEntity(
    @PrimaryKey(autoGenerate = true)
    var ElementId: Long = 0L,
    @ColumnInfo(name = "name")
    var ElementName: String = "",
    @ColumnInfo(name = "Quantity")
    var ElementQuantity: Int = 0,
    @ColumnInfo(name = "Description")
    var ElementDescription: String = ""

)