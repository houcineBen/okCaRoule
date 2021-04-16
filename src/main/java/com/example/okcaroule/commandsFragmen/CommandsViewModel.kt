package com.example.okcaroule

import android.app.Application
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.*
import com.example.okcaroule.commandsFragment.dataBase.ElementDao
import com.example.okcaroule.commandsFragment.dataBase.ElementsEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CommandsViewModel(val db: ElementDao, application: Application): AndroidViewModel(application) {
    val data = db.getAll()
    private val newElement = MutableLiveData<ElementsEntity?>()


    val _newElement: LiveData<ElementsEntity?>
        get() = newElement

    fun onAddElement() {
        viewModelScope.launch {
            newElement.value = ElementsEntity()

        }
    }
    suspend fun Ins(Element: ElementsEntity) {
        withContext(IO) {
            db.insertElement(Element)
        }

    }
    fun Insert(Element: ElementsEntity, application: Application) {
        viewModelScope.launch {
            Ins(Element)
            Toast.makeText(application, "element successfully entered", Toast.LENGTH_SHORT).show()
        }
    }


    fun delete(Element: ElementsEntity) {
        viewModelScope.launch {
            del(Element)
        }
    }

    private suspend fun del(element: ElementsEntity) {
        withContext(IO) {
            db.delete(element)
        }
    }
    fun cleanAll() {
        viewModelScope.launch {
            withContext(IO) {
                db.deleteAll()
            }
        }
    }
    fun closeKeyboard(view: View, inputService: InputMethodManager) {
        val imm = inputService
        imm.hideSoftInputFromWindow(view.windowToken, 0)

    }
    fun onDeleteAllSelected() {
        viewModelScope.launch {
            withContext(IO) {
                db.deleteAll()
            }
        }
    }
    fun setEventDone(){
        newElement.value = null
    }
    fun update(Element: ElementsEntity) {
        viewModelScope.launch {
            withContext(IO) {
                db.update(Element)
            }
        }
    }



}
