package com.malfaa.notaapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.malfaa.notaapp.R
import com.malfaa.notaapp.databinding.MainNotasFragmentBinding
import com.malfaa.notaapp.room.Nota
import com.malfaa.notaapp.room.NotaDatabase

class MainNotasFragment : Fragment() {

    companion object {
        fun newInstance() = MainNotasFragment()
    }

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

        binding.adcBtn.setOnClickListener {
            viewModel.adicionandoAoDatabase(Nota(binding.notaTexto.text.toString()))
            Toast.makeText(context, "${binding.notaTexto.text}" ,Toast.LENGTH_LONG).show() // TODO: 10/08/2021 arrumar número máx. de caracteres por linha
            binding.notaTexto.setText("")                                                       // TODO: 10/08/2021 arrumar ordem de display do recyclerview
        }

        val adapter = MainAdapter()
        binding.notaRecycler.adapter = adapter

        viewModel.dataSet.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })

    }
}