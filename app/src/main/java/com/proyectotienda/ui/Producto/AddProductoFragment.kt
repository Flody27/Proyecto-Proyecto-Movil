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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.proyectotienda.adapter.TallaAdminAdapter

class AddProductoFragment : Fragment() {

    private lateinit var productoViewModel: ProductoViewModel

    private var _binding: FragmentAddProductoBinding? = null
    private val binding get() = _binding!!
    private lateinit var producto : Producto

    private var tallas: List<String?> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         productoViewModel =
            ViewModelProvider(this).get(ProductoViewModel::class.java)

        producto = Producto("","",0,"",ArrayList())

        _binding = FragmentAddProductoBinding.inflate(inflater, container, false)
        binding.btAddProducto.setOnClickListener{addProducto()}

        binding.btAgregarTalla.setOnClickListener {
            agregarTalla()
            val tallasAdpater = TallaAdminAdapter()
            val recilador = binding.RecicladorTallas
            recilador.adapter = tallasAdpater
            recilador.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL,false)
            tallasAdpater.setListaTallas(tallas)
        }



        return binding.root
    }

    private fun agregarTalla() {
        if(binding.etTallasProd.text.isNotEmpty()){
            tallas+=binding.etTallasProd.text.toString()
            binding.etTallasProd.setText("")
        }else{
            Toast.makeText(requireContext(),"Campo vacio",Toast.LENGTH_LONG).show()
        }

    }

    private fun addProducto() {
        val nombreProducto = binding.etNombreProd.text.toString()
        if (nombreProducto.isNotEmpty()) {


            val precioProducto = binding.etPrecioProd.text.toString().toInt()
            val coloresProducto = binding.etColoresProd.text.toString()
            producto = Producto("",nombreProducto,precioProducto,coloresProducto,tallas)
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


