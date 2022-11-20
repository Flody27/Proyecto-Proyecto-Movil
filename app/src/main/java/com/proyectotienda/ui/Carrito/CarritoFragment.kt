package com.proyectotienda.ui.Carrito

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.proyectotienda.adapter.CompraAdapter
import com.proyectotienda.adapter.ProductoAdapter
import com.proyectotienda.databinding.FragmentCarritoBinding
import com.proyectotienda.viewModel.ComprasViewModel

class CarritoFragment : Fragment() {

    private var _binding: FragmentCarritoBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//            val carritoViewModel =
//                ViewModelProvider(this)[ComprasViewModel::class.java]

        _binding = FragmentCarritoBinding.inflate(inflater, container, false)

        binding.btRealizarCompra.setOnClickListener{

        }

        // Rycler view
        val carritoAdapter = CompraAdapter()
        val reciclador = binding.recicladorCarrito
        reciclador.adapter = carritoAdapter
        reciclador.layoutManager = LinearLayoutManager(requireContext())



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}