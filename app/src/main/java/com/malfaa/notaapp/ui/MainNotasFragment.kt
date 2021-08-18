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
import androidx.lifecycle.ViewModelProvider
import com.malfaa.notaapp.R
import com.malfaa.notaapp.databinding.MainNotasFragmentBinding
import com.malfaa.notaapp.room.Nota
import com.malfaa.notaapp.room.NotaDatabase
import com.malfaa.notaapp.databinding.NotaBinding as NotaBinding1


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


        val adapter = context?.let { MainAdapter(it) }
        binding.notaRecycler.adapter = adapter


        binding.adcBtn.setOnClickListener {
            if(binding.notaTexto.text.isNotEmpty()){
                viewModel.adicionandoNota(Nota(binding.notaTexto.text.toString()))
                Toast.makeText(context, "Adicionado" ,Toast.LENGTH_LONG).show()
                binding.notaTexto.setText("")
            }else{
                //Log.d("ValidaTeste FINAL 1 ->", "${viewModel.teste.value}")
                viewModel.validaTeste()
                Toast.makeText(context, "Insira algum caractére!" ,Toast.LENGTH_LONG).show()
            }

        }

        binding.edtBtn.setOnClickListener{
            viewModel.atualizandoNota(Nota(binding.notaTexto.text.toString()))
            Toast.makeText(context, "Editado" ,Toast.LENGTH_LONG).show()
            binding.notaTexto.setText("")
            viewModel.reverteTeste()
        }


        viewModel.teste.observe(viewLifecycleOwner, {
                teste ->
            if(teste) {
                Log.d("Deu", "Teste Observer Passou")
                binding.adcBtn.isGone
                binding.edtBtn.isVisible
            }else{
                binding.edtBtn.isGone
                binding.adcBtn.isVisible
            }
        })


        viewModel.dataSet.observe(viewLifecycleOwner, {
            it?.let {
                adapter?.submitList(it)
                Log.d("teste", "${viewModel.teste.value}")
            }
        })
    }
}
