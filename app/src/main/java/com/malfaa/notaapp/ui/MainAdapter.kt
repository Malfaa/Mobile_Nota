package com.malfaa.notaapp.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.malfaa.notaapp.R
import com.malfaa.notaapp.databinding.NotaBinding
import com.malfaa.notaapp.room.Nota
import com.malfaa.notaapp.room.NotaDatabase


class MainAdapter : ListAdapter<Nota, MainAdapter.ViewHolder>(NotaDiffCallback()){


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

        val item = getItem(position)
        holder.bind(item)
        // TODO: 10/08/2021 consertar o apagar do database
        // FIXME: 11/08/2021 problema é que ele não ta conseguindo referenciar para o viewmodel
        // FIXME: 11/08/2021 problema está neste campo do onBindViewHolder
        holder.binding.deletarNota.setOnClickListener{
                Log.d("Info De Deletar", item.nota)
                MainNotasViewModel(dataSource).deleteNota = item
                MainNotasViewModel(dataSource).teste.value =  true

            // FIXME: 11/08/2021 referenciar corretamente ao viewmodel 
        }
    }

}

/*
        val context = MainNotasFragment.newInstance().requireActivity()
        val dataSource = NotaDatabase.getDatabase(context).notaDao()

holder.binding.deletarNota.setOnClickListener{
            Log.d("Info De Deletar", item.nota)

            if (dataSource != null) {
                MainNotasViewModel(dataSource).deletandoDoDatabase(item)
            }
        }*/