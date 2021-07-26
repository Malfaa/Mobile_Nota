package com.example.notaapp.room

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotaRepository (private val dao: NotaDao){

    fun getDataFromDatabase():LiveData<List<Nota>> = dao.retornarNotas()

    suspend fun salvandoDataNoDatabase(nota: Nota) = dao.inserir(nota)
}