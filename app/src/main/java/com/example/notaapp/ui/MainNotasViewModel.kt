package com.example.notaapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.notaapp.room.Nota
import com.example.notaapp.room.NotaDao
import com.example.notaapp.room.NotaDatabase
import com.example.notaapp.room.NotaRepositorio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
//troquei aqui val repositorio -> database: NotaDao
class MainNotasViewModel(val repositorio: NotaRepositorio, application: Application) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun adicionandoDatabase(nota: Nota){
        uiScope.launch {
            repositorio.adicionandoNota(nota)
        }
    }

}