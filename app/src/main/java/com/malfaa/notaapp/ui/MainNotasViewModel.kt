package com.malfaa.notaapp.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.malfaa.notaapp.room.Nota
import com.malfaa.notaapp.room.NotaDao
import com.malfaa.notaapp.room.NotaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainNotasViewModel(val database: NotaDao) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val dataSet = database.retornarNotas()

    fun adicionandoAoDatabase(nota: Nota){
        uiScope.launch {
            database.inserir(nota)
        }
    }

    fun deletandoDoDatabase(nota:Nota){
        uiScope.launch {
            database.deletar(nota)
        }
    }

}