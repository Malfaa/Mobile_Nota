package com.example.notaapp.room

class NotaRepositorio(private val notaDao: NotaDao) {

    suspend fun adicionandoNota(nota: Nota){
        notaDao.inserir(nota)
    }

    suspend fun deletandoNota(nota:Nota){
        notaDao.deletar(nota)
    }

    suspend fun notas(){
        notaDao.retornarNotas()
    }

}