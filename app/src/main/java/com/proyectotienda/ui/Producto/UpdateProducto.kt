package com.proyectotienda.ui.Producto

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.proyectotienda.R
import com.proyectotienda.adapter.TallaAdminAdapter
import com.proyectotienda.databinding.FragmentUpdateProductoBinding
import com.proyectotienda.model.Producto
import com.proyectotienda.viewModel.ProductoViewModel

class UpdateProducto : Fragment() {

    private val args by navArgs<UpdateProductoArgs>()

    private lateinit var productoViewModel: ProductoViewModel

    private var _binding: FragmentUpdateProductoBinding? = null

    private val binding get() = _binding!!

    private lateinit var tallasAdapter : TallaAdminAdapter

    private  lateinit var recilador : RecyclerView

    private var tallas: List<String?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productoViewModel =
            ViewModelProvider(this).get(ProductoViewModel::class.java)

        _binding =FragmentUpdateProductoBinding.inflate(inflater,container,false)

        tallas = args.producto.tallas

        binding.etNombreProd.setText(args.producto.nombre)
        binding.etPrecioProd.setText(args.producto.precio.toString())
        Glide.with(binding.root.context)
            .load(args.producto.imagen)
            .into(binding.imageView4)


        tallasAdapter = TallaAdminAdapter()
        recilador = binding.RecicladorTallas
        lista()
        tallasAdapter.setListaTallas(tallas)

        binding.btUpdateProducto.setOnClickListener {
            actualizarProducto()
        }

        binding.btDeleteProducto.setOnClickListener {
            eliminarProducto()
        }

        binding.btAgregarTalla.setOnClickListener {
            agregarTalla()
            lista()
            tallasAdapter.setListaTallas(tallas)

        }


        return binding.root
    }

    private fun agregarTalla() {
        if(binding.etTallasProd.text.isNotEmpty()){
            tallas +=binding.etTallasProd.text.toString()
            binding.etTallasProd.setText("")

        }else{
            Toast.makeText(requireContext(),"Campo vacio",Toast.LENGTH_LONG).show()
        }

    }

    private fun lista(){
        recilador.adapter = tallasAdapter
        recilador.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL,false)
    }


    private fun eliminarProducto() {
        val alerta = AlertDialog.Builder(requireContext())
        alerta.setTitle(R.string.title_eliminar_producto)
        alerta.setMessage(getString(R.string.pregunta_delete_producto)+":${args.producto.nombre} ?")
        alerta.setPositiveButton(getString(R.string.msg_si)){_,_ ->
            productoViewModel.deleteProducto(args.producto)
            Toast.makeText(requireContext(),getString(R.string.msg_producto_deleted),Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateProducto_to_navigation_productos)
        }
        alerta.setNegativeButton(getString(R.string.msg_no)){_,_ ->}
        alerta.create().show()
    }


    private fun actualizarProducto() {
        val nombre = binding.etNombreProd.text.toString()
        if(nombre.isNotEmpty()){
            val precio = binding.etPrecioProd.text.toString().toInt()
            val color = binding.etColoresProd.text.toString()
            val producto = Producto(args.producto.id,nombre,precio,color,tallas)
            productoViewModel.addProducto(producto)
            Toast.makeText(requireContext(),getString(R.string.msg_producto_updated), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateProducto_to_navigation_productos)
        }else{
            Toast.makeText(requireContext(),getString(R.string.err_producto_updated), Toast.LENGTH_SHORT).show()
        }
    }

}