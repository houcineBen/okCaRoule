package com.example.okcaroule.locationFragment.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ClientDao {
    @Insert
    fun insert(Element: ClientEntity)
    @Query("SELECT * FROM client_entity WHERE clientId = :id")
    fun get(id: Long): ClientEntity
    @Query("SELECT * FROM client_entity")
    fun getAll(): LiveData<List<ClientEntity>>
    @Query("DELETE FROM client_entity")
    fun deleteAll()
    @Query("DELETE FROM client_entity WHERE clientId = :id")
    fun delete(id: Long)
    @Query("SELECT bikesCount FROM client_entity")
    fun bikesCount(): List<Int>
}