package com.example.okcaroule.commandsFragment.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.okcaroule.locationFragment.database.ClientDao
import com.example.okcaroule.locationFragment.database.ClientEntity

@Database(entities = [ElementsEntity::class, ClientEntity::class], version = 4, exportSchema = false)
abstract class ElementDatabase : RoomDatabase() {
    abstract val elementDao: ElementDao
    abstract val clientDao: ClientDao

    companion object {
        @Volatile
        private var INSTANCE: ElementDatabase? = null

        fun getDatabaseInstance(context: Context): ElementDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ElementDatabase::class.java,
                        "element_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance

                }
                return instance
            }


        }
    }
}