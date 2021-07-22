package com.example.notaapp.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notaapp.room.Nota
import com.example.notaapp.room.NotaDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

//troquei aqui val repositorio -> database: NotaDao
class MainNotasViewModel(val database: NotaDao, application: Application) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private lateinit var lista: LiveData<List<Nota>>

//    private val _onClick = MutableLiveData<Boolean>(false)
//    val onClick : MutableLiveData<Boolean>
//        get() = _onClick
//    fun enviandoNota(){
//        _onClick.value = true
//    }
//
//    fun fechandoNota(){
//        _onClick.value = false
//    }

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

    fun retornandoDatabase(): LiveData<List<Nota>>{
        uiScope.launch {
            lista = database.retornarNotas()
        }
        return lista
    }

}