package com.example.notaapp.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notaapp.room.Nota
import com.example.notaapp.room.NotaDao
import com.example.notaapp.room.NotaDatabase
import com.example.notaapp.room.NotaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

//troquei aqui val repositorio -> database: NotaDao
class MainNotasViewModel(val database: NotaDao, application: Application) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _dataList = MutableLiveData<List<Nota>>()
    val dataList : LiveData<List<Nota>> = _dataList

    private var repositorio:NotaRepository = NotaRepository(database)


    fun adicionandoDatabase(nota: Nota){
        uiScope.launch {
            database.inserir(nota)
        }
    }

    fun deletandoDatabase(nota:Nota){
        uiScope.launch {
            database.deletar(nota)
        }
    }

    fun data() = uiScope.launch {
        val list = repositorio.getDataFromDatabase()
        _dataList.value = list.value
    }

}