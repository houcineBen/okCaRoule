package com.example.okcaroule

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.okcaroule.commandsFragment.dataBase.ElementDao

class ViewModelFactory(
    private val database: ElementDao,
    private val application: Application
): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CommandsViewModel::class.java)){
            return CommandsViewModel(database, application) as T
        }
        throw IllegalArgumentException ("UnknownViewModel")
    }

}