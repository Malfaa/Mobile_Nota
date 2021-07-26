package com.example.notaapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notaapp.room.NotaDao
import com.example.notaapp.room.NotaDatabase

class MainNotasViewModelFactory(private val dataSource: NotaDao, private val application: Application): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainNotasViewModel::class.java)) {
            return MainNotasViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("ViewModel desconhecido")
    }
}