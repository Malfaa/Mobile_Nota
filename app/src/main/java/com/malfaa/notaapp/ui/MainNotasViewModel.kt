package com.malfaa.notaapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.malfaa.notaapp.room.Nota
import com.malfaa.notaapp.room.NotaDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainNotasViewModel(val database: NotaDao) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val dataSet = database.retornarNotas()

    fun adicionandoNota(nota: Nota){
        uiScope.launch {
            database.inserir(nota)
        }
    }

    fun deletandoNota(nota: Nota){
        uiScope.launch {
            database.deletar(nota)
        }
    }

    fun atualizandoNota(nota: Nota){
        uiScope.launch {
            database.atualizar(nota)
        }
    }
}