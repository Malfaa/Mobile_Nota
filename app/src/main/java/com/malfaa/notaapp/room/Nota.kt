package com.malfaa.notaapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "notas")
data class Nota(
    val nota: String
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "nota_id")
    var notaId: Int = 0
}
