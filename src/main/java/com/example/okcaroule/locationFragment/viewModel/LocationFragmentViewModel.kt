package com.example.okcaroule.locationFragment.viewModel

import android.app.Application
import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.service.autofill.DateValueSanitizer
import androidx.core.util.Pair
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.okcaroule.locationFragment.database.ClientDao
import com.example.okcaroule.locationFragment.database.ClientEntity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.properties.Delegates


class LocationFragmentViewModel(val db: ClientDao, sharedPreferences: SharedPreferences, app: Application) : ViewModel() {
    var bikes = 0
    val data = db.getAll()
    val availableBikes = MutableLiveData<Int>(0)
    val bookedBikes = MutableLiveData<Int>(0)
    val totalBikes = MutableLiveData<Int>(sharedPreferences.getInt("totalBikes", 0))
    val longClick = MutableLiveData<Int>(0)
    private val newClient = MutableLiveData<ClientEntity>()
    val _newClient: LiveData<ClientEntity>
        get() = newClient

    init {
        newClient.value = ClientEntity()

    }


    fun onAddBikesClicked(state: Int) {
        if (state == 0) {
            totalBikes.value = totalBikes.value!!.plus(1)
            availableBikes.value = totalBikes.value!! - bookedBikes.value!!
        } else {
            if (totalBikes.value!! > bookedBikes.value!!) {
                totalBikes.value = totalBikes.value!!.minus(1)
                availableBikes.value = totalBikes.value!! - bookedBikes.value!!
            }
        }
    }

    var pos = 0
    fun onAddBikesLongClick() {
        if (pos == 0) {
            longClick.value = 0
            pos = 1
        } else {
            longClick.value = 1
            pos = 0
        }
    }

    fun dialogParser(client: ClientEntity) {
        viewModelScope.launch {
            withContext(IO) {
                db.insert(client)
            }
        }
    }
    fun dateDialog(): MaterialDatePicker<Pair<Long, Long>> {
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        val datePicker = builder.build()
        return datePicker


        
//        val style = AlertDialog.THEME_HOLO_LIGHT
//        val datePicker = DatePickerDialog(activity!!,style, listener, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
//        datePicker.show()
    }
    fun changeBooked(operation: String, bikes: Int){
        if (operation == "plus"){
        bookedBikes.value = bookedBikes.value!!.plus(bikes)
        availableBikes.value = totalBikes.value!! - bookedBikes.value!!
        } else {
            bookedBikes.value = bookedBikes.value!!.minus(bikes)
            availableBikes.value = totalBikes.value!! - bookedBikes.value!!
        }
    }
    fun deleteItem(id: Long) {
        viewModelScope.launch {
            withContext(IO) {
                db.delete(id)
            }
        }
    }
    fun calculateBikes() {
        viewModelScope.launch {
            withContext(IO) {
                bikes = db.bikesCount().sum()
            }
            bookedBikes.value = bikes
            availableBikes.value = totalBikes.value!!.minus(bookedBikes.value!!)
        }

    }


}