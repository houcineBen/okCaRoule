package com.example.okcaroule.commandsFragment.dataBase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface ElementDao {
    @Insert
    fun insertElement(Element: ElementsEntity)
    @Delete
    fun deleteElement(Element: ElementsEntity)
    @Query("SELECT * FROM command_table ORDER BY ElementId DESC")
    fun getAll(): LiveData<List<ElementsEntity>>
    @Query("SELECT * FROM Command_table WHERE ElementId = :ElementId")
    fun get(ElementId: Long): ElementsEntity
    @Delete
    fun delete(Element: ElementsEntity)
    @Query("DELETE FROM command_table")
    fun deleteAll()
    @Update
    fun update(Element: ElementsEntity)
}