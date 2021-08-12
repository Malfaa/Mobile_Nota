package com.malfaa.notaapp.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.malfaa.notaapp.R
import com.malfaa.notaapp.databinding.NotaBinding
import com.malfaa.notaapp.room.Nota
import com.malfaa.notaapp.room.NotaDatabase
import java.lang.Exception


class MainAdapter(val context: Context) : ListAdapter<Nota, MainAdapter.ViewHolder>(NotaDiffCallback()){


    class ViewHolder private constructor(val binding: NotaBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item : Nota){
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NotaBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class NotaDiffCallback : DiffUtil.ItemCallback<Nota>() {
        override fun areItemsTheSame(oldItem: Nota, newItem: Nota): Boolean {
            return oldItem.notaId == newItem.notaId
        }

        override fun areContentsTheSame(oldItem: Nota , newItem: Nota): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataSource = NotaDatabase.getDatabase(context).notaDao()

        val item = getItem(position)
        holder.bind(item)

        holder.binding.deletarNota.setOnClickListener{
            try {
                Log.d("Info Ao Deletar", item.nota)
                MainNotasViewModel(dataSource).deletandoDoDatabase(item)
            }catch (e: Exception){
                Log.d("Erro ao deletar", e.toString())
            }
        }

        /*
        * */
    }
}