package com.malfaa.notaapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.malfaa.notaapp.databinding.NotaBinding
import com.malfaa.notaapp.room.Nota
import com.malfaa.notaapp.room.NotaDatabase

class MainAdapter(private val context: Context) : ListAdapter<Nota, MainAdapter.ViewHolder>(NotaDiffCallback()){
     private val dataSource = NotaDatabase.getDatabase(context).notaDao()

    var editTeste = MutableLiveData<Boolean>()
    lateinit var notaAEditar:Nota

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
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Nota, newItem: Nota): Boolean {
            return oldItem === newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.binding.deletarNota.setOnClickListener{
            try {
                MainNotasViewModel(dataSource).deletandoNota(item)
                Toast.makeText(context, "Deletado", Toast.LENGTH_LONG).show()
            }catch (e: Exception){
                Toast.makeText(context, "Erro: $e",Toast.LENGTH_SHORT).show()
            }
        }

        holder.binding.nota.setOnLongClickListener{
            try {
                Log.d("Info", "Clicado - ${item.nota}")
                notaAEditar = item
                editTeste.value = true
            }catch (e: Exception){
                Log.d("Erro ao editar", e.toString())
            }
            true
        }
    }
}
