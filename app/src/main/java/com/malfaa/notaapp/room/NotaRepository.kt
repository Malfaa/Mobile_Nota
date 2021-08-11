package com.malfaa.notaapp.room

import androidx.lifecycle.LiveData

class NotaRepository (private val dao: NotaDao){

    fun getDataFromDatabase():LiveData<List<Nota>> = dao.retornarNotas()

    suspend fun deletandoNota(nota: Nota) = dao.deletar(nota)
}