package com.malfaa.notaapp.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
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


        val adapter = context?.let { MainAdapter(it) }
        binding.notaRecycler.adapter = adapter


        binding.adcBtn.setOnClickListener {
            if(binding.notaTexto.text.isNotEmpty()){
                viewModel.adicionandoNota(Nota(binding.notaTexto.text.toString()))
                Toast.makeText(context, "Adicionado" ,Toast.LENGTH_LONG).show()
                binding.notaTexto.setText("")
            }else{
                Toast.makeText(context, "Insira algum caractére" ,Toast.LENGTH_LONG).show()
            }
        }

        binding.edtBtn.setOnClickListener{
            if(binding.notaTexto.text.toString() != adapter?.notaAEditar?.nota){
                adapter?.notaAEditar?.nota = binding.notaTexto.text.toString()

                val novaNota = adapter!!.notaAEditar
                viewModel.atualizandoNota(novaNota)

                Toast.makeText(context, "Editado" ,Toast.LENGTH_LONG).show()
                false.also { adapter.editTeste.value = it } //fixme alterado aqui
                binding.notaTexto.setText("")
                // FIXME: 23/08/2021 setar um refresher pra quando for alterado a nota
                binding.notaTexto.clearFocus()
            }else{
                false.also { adapter.editTeste.value = it } //fixme alterado aqui
                binding.notaTexto.clearFocus()
                Toast.makeText(context, "Nada alterado" ,Toast.LENGTH_LONG).show()
            }
        }

        // TODO: 19/08/2021 RESOLVER PROBLEMA DE FOCO DO EDITTEXT E DE ATUALIZAR O REYCLERVIEW QUANDO EDITADO
        adapter?.editTeste?.observe(viewLifecycleOwner, {
            if (adapter.editTeste.value == true) {
                binding.adcBtn.visibility = View.GONE
                binding.edtBtn.visibility = View.VISIBLE
                binding.MainNotasFragment.setBackgroundColor(Color.parseColor("#fafafa"))
                binding.notaTexto.setText(adapter.notaAEditar.nota)

                // TODO: 23/08/2021 PARA TESTE binding.notaTexto.setNota(adapter.notaAEditar)
            }
            binding.MainNotasFragment.setOnClickListener {
                binding.edtBtn.visibility = View.GONE
                binding.adcBtn.visibility = View.VISIBLE
                adapter.editTeste.value = false
                binding.notaTexto.setText("")
                binding.MainNotasFragment.setBackgroundColor(Color.WHITE)
                Log.d("EditTeste", adapter.editTeste.value.toString())
            }

        })

        viewModel.dataSet.observe(viewLifecycleOwner, {
            it?.let {
                adapter?.submitList(it)
            }
        })
    }

}
