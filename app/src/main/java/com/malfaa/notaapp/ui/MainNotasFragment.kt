package com.malfaa.notaapp.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
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

        fun View.hideKeyboard() {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }


        binding.adcBtn.setOnClickListener {
            if(binding.notaTexto.text.isNotEmpty()){
                viewModel.adicionandoNota(Nota(binding.notaTexto.text.toString()))
                adapter?.editTeste?.value = false
                Toast.makeText(context, "Adicionado" ,Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(context, "Insira algum caractére" ,Toast.LENGTH_SHORT).show()
                adapter?.editTeste?.value = false
            }
        }

        binding.edtBtn.setOnClickListener{
            if(binding.notaTexto.text.toString() != adapter?.notaAEditar?.nota){
                adapter?.notaAEditar?.nota = binding.notaTexto.text.toString()
                adapter?.editTeste?.value = false
                Toast.makeText(context, "Editado" ,Toast.LENGTH_SHORT).show()

                val novaNota = adapter!!.notaAEditar
                viewModel.atualizandoNota(novaNota)
            }else{
                adapter.editTeste.value = false
                Toast.makeText(context, "Nada alterado" ,Toast.LENGTH_SHORT).show()
            }
        }

        // TODO: 25/08/2021 Arrumar quando em modo Landscape
        // TODO: 25/08/2021 Arrumar edittext (?)

        adapter?.editTeste?.observe(viewLifecycleOwner, {
            if (adapter.editTeste.value == true) {
                binding.adcBtn.visibility = View.GONE
                binding.edtBtn.visibility = View.VISIBLE
                binding.MainNotasFragment.setBackgroundColor(Color.parseColor("#fafafa"))
                binding.notaTexto.setText(adapter.notaAEditar.nota)

            }else{
                binding.notaTexto.setText("")
                binding.notaTexto.hideKeyboard()
                binding.edtBtn.visibility = View.GONE
                binding.adcBtn.visibility = View.VISIBLE
                binding.MainNotasFragment.setBackgroundColor(Color.WHITE)
            }
            binding.MainNotasFragment.setOnClickListener {
                adapter.editTeste.value = false
            }

            binding.notaRecycler.setOnClickListener{
                adapter.editTeste.value = false
            }
        })

        viewModel.dataSet.observe(viewLifecycleOwner, {
            it?.let {
                adapter?.submitList(it.toMutableList())
            }
        })
    }

}
