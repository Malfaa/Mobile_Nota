package com.malfaa.notaapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Nota::class], version = 1, exportSchema = false)
abstract class NotaDatabase : RoomDatabase() {

    abstract fun notaDao(): NotaDao

    private class NotaDatabaseCallback : RoomDatabase.Callback()

    companion object {
        @Volatile
        private var INSTANCE: NotaDatabase? = null

        fun getDatabase(context: Context): NotaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotaDatabase::class.java,
                    "nota_database"
                ).addCallback(NotaDatabaseCallback()).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }

}