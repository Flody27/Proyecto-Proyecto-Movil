package com.proyectotienda.ui.Producto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.proyectotienda.R
import com.proyectotienda.databinding.FragmentAddProductoBinding
import com.proyectotienda.model.Producto
import com.proyectotienda.viewModel.ProductoViewModel
import androidx.navigation.fragment.findNavController

class AddProductoFragment : Fragment() {

    private lateinit var productoViewModel: ProductoViewModel

    private var _binding: FragmentAddProductoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         productoViewModel =
            ViewModelProvider(this).get(ProductoViewModel::class.java)

        _binding = FragmentAddProductoBinding.inflate(inflater, container, false)
        binding.btAddProducto.setOnClickListener{addProducto()}



        return binding.root
    }

    private fun addProducto() {
        val nombreProducto = binding.etNombreProd.text.toString()
        if (nombreProducto.isNotEmpty()) {

            val precioProducto = binding.etPrecioProd.text.toString().toDouble()
            val coloresProducto = binding.etColoresProd.text.toString()
            val tallasProducto = binding.etTallasProd.text.toString()
            val producto = Producto("",nombreProducto,precioProducto,coloresProducto,tallasProducto)
             productoViewModel.addProducto(producto)
            Toast.makeText(requireContext(),getString(R.string.msg_add_producto),Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addProductoFragment_to_navigation_productos)
        }else{
            Toast.makeText(requireContext(),getString(R.string.msg_data),Toast.LENGTH_LONG).show()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

