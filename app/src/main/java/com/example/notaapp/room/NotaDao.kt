package com.example.notaapp.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotaDao {

    @Query("SELECT * FROM notas")
    suspend fun retornarNotas(): List<Nota>

    @Insert
    suspend fun inserir(nota:Nota)

    @Delete
    suspend fun deletar(nota:Nota)

}