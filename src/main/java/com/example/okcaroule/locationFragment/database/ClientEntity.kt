package com.example.okcaroule.locationFragment.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Client_entity")
data class ClientEntity (
    @PrimaryKey (autoGenerate = true)
    var clientId: Long  = 0L,
    @ColumnInfo
    var clientName: String = "",
    @ColumnInfo
    var clientPhone: String = "",
    @ColumnInfo
    var bikesCount: Int = 0,
    @ColumnInfo
    var locationDuration: String = "",
    @ColumnInfo
    var caution: String = "Caution",
    @ColumnInfo
    var price: Int = 0,
    @ColumnInfo
    var locks: Int = 0


    )