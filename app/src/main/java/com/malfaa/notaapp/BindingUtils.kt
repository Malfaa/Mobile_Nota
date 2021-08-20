package com.malfaa.notaapp.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.malfaa.notaapp.room.Nota


@BindingAdapter("setNota")
fun TextView.setNota(item: Nota){
    text =  item.nota
}

