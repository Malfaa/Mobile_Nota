package com.example.notaapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notaapp.R
import com.example.notaapp.databinding.MainNotasFragmentBinding
import com.example.notaapp.databinding.NotaBinding
import com.example.notaapp.databinding.NotaBindingImpl
import com.example.notaapp.room.Nota

class MainAdapter (private val lista: List<Nota>): RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val itemView = LayoutInflater.from(parent.context).inflate(R.layout.nota, parent, false)
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = NotaBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val itemAtual = lista[position]
//        holder.notaTxt.text = itemAtual.nota
        holder.bind(lista[position])
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    class ViewHolder(val binding: NotaBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Nota){
            binding.notaTexto.setText(item.nota)
        }


    }
}


