package com.malfaa.notaapp.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.malfaa.notaapp.databinding.NotaBinding
import com.malfaa.notaapp.room.Nota
import com.malfaa.notaapp.room.NotaDatabase

class MainAdapter(private val context: Context) : ListAdapter<Nota, MainAdapter.ViewHolder>(NotaDiffCallback()){
     private val dataSource = NotaDatabase.getDatabase(context).notaDao()

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

//bp
        //FUYNCIAINSA
        holder.binding.deletarNota.setOnClickListener{
            try {
                Log.d("Info Ao Deletar", item.nota)
                MainNotasViewModel(dataSource).deletandoNota(item)
                Toast.makeText(context, "Deletado", Toast.LENGTH_LONG).show()
            }catch (e: Exception){
                Log.d("Erro ao deletar", e.toString())
            }
        }

        holder.binding.nota.setOnLongClickListener{
            try {
                Log.d("Info", "Clicado - ${item.nota}")

                Log.d("Teste PRÉ ->", "${MainNotasViewModel(dataSource).teste.value}")
                MainNotasViewModel(dataSource).validaTeste()
                Log.d("Teste PÓS ->", "${MainNotasViewModel(dataSource).teste.value}")

            }catch (e: Exception){
                Log.d("Erro ao editar", e.toString())
            }
            true
        }
// FIXME: 17/08/2021 o valor que é colocado no validaTeste() não está mudando a váriavel teste.
//  Pelo fragmento colocando em algum botão ele funciona normalmente, o porquê do adapter não
//  realizar a chamada eu não sei. Um metódo que funciona é colocar o editar e talvez o deletar juntos
//Testando

    }
}
