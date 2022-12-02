package com.proyectotienda.ui.Producto

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.proyectotienda.R
import com.proyectotienda.adapter.ProductoAdapter
import com.proyectotienda.databinding.FragmentProductoBinding
import com.proyectotienda.model.Producto
import com.proyectotienda.viewModel.ProductoViewModel

class ProductoFragment : Fragment() {

    private var _binding: FragmentProductoBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val productoViewModel =
            ViewModelProvider(this)[ProductoViewModel::class.java]

        _binding = FragmentProductoBinding.inflate(inflater, container, false)

        // Nav para el fragment add producto
        binding.btAgregar.setOnClickListener {

            findNavController().navigate(R.id.action_navigation_productos_to_addProductoFragment)
        }


        val productoAdapter = ProductoAdapter()
        val reciclador = binding.Reciclador
        reciclador.adapter = productoAdapter
        reciclador.layoutManager = GridLayoutManager(requireContext(),2)

        productoViewModel.getProductos.observe(viewLifecycleOwner) { productos ->
            productoAdapter.setListaProductos(productos)
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}