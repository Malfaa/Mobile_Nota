package com.malfaa.notaapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.malfaa.notaapp.R
import com.malfaa.notaapp.databinding.MainNotasFragmentBinding
import com.malfaa.notaapp.room.Nota
import com.malfaa.notaapp.room.NotaDatabase


class MainNotasFragment : Fragment() {

    private lateinit var viewModel: MainNotasViewModel
    private lateinit var binding: MainNotasFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_notas_fragment, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val dataSource = NotaDatabase.getDatabase(application).notaDao()
        val viewModelFactory = MainNotasViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainNotasViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        val adapter = context?.let { MainAdapter(it) }
        binding.notaRecycler.adapter = adapter


        binding.adcBtn.setOnClickListener {
            viewModel.adicionandoNota(Nota(binding.notaTexto.text.toString()))
            Toast.makeText(context, "Adicionado" ,Toast.LENGTH_LONG).show()
            binding.notaTexto.setText("")
        }
        binding.edtBtn.setOnClickListener{
            viewModel.atualizandoNota(Nota(binding.notaTexto.text.toString()))
            Toast.makeText(context, "Editado" ,Toast.LENGTH_LONG).show()
            binding.notaTexto.setText("")
        }


        viewModel.dataSet.observe(viewLifecycleOwner, {
            it?.let {
                adapter?.submitList(it)
            }
        })


        // TODO: 13/08/2021 objetivo é, o valor da nota clicada passe para o text do notaTexto, assim só realizando o update

        // TODO: 13/08/2021 problema é aqui, a função do viewmodel n ta retornando
        viewModel.teste.observe(viewLifecycleOwner, {
            it?.let {
                Log.d("Passado:", "ViewModel funcionando e teste passado")
                binding.adcBtn.isGone
                binding.edtBtn.isVisible
            }})


//        val gesto = object : Swipe() {
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                super.onSwiped(viewHolder, direction)
//
//                when(direction){
//                    ItemTouchHelper.LEFT -> {
//                        binding.adcBtn.isGone
//                        binding.edtBtn.isVisible
//                    }
//                    ItemTouchHelper.RIGHT -> {
//                        binding.adcBtn.isGone
//                        binding.edtBtn.isVisible
//                    }
//                }
//            }
//        }
//
//        val swipeHelper = ItemTouchHelper(gesto)
//        swipeHelper.attachToRecyclerView(binding.notaRecycler)
    }
}
// TODO: 12/08/2021 Arquivar todos(?) Swipe