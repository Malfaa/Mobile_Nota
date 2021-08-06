package com.malfaa.notaapp.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotaDao {

    @Query("SELECT * FROM notas")
    fun retornarNotas(): LiveData<List<Nota>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(nota:Nota)

    @Delete
    suspend fun deletar(nota:Nota)

}