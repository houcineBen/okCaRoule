package com.example.okcaroule.locationFragment.viewModel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.okcaroule.locationFragment.database.ClientDao

class ClientViewModelFactory(val db: ClientDao, val sharedPreferences: SharedPreferences, val app: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LocationFragmentViewModel::class.java)){
            return LocationFragmentViewModel(db, sharedPreferences, app) as T
        }
        throw IllegalArgumentException ("UnknownViewModel")
    }

}