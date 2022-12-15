package com.proyectotienda.ui.Producto

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.proyectotienda.R
import com.proyectotienda.adapter.ProductoAdapter
import com.proyectotienda.databinding.FragmentProductoBinding
import com.proyectotienda.model.Producto
import com.proyectotienda.viewModel.ProductoViewModel
import com.proyectotienda.viewModel.UsuarioViewModel
import kotlin.math.log

class ProductoFragment : Fragment() {

    private var _binding: FragmentProductoBinding? = null

    private val binding get() = _binding!!

    private lateinit var usuarioViewModel: UsuarioViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val productoViewModel =
            ViewModelProvider(this)[ProductoViewModel::class.java]

        usuarioViewModel =
            ViewModelProvider(this)[UsuarioViewModel::class.java]

        _binding = FragmentProductoBinding.inflate(inflater, container, false)

        val email = Firebase.auth.currentUser?.email
        var rol = ""
        val productoAdapter = ProductoAdapter()
        val reciclador = binding.Reciclador
        reciclador.adapter = productoAdapter
        reciclador.layoutManager = GridLayoutManager(requireContext(), 2)

        productoViewModel.getProductos.observe(viewLifecycleOwner) { productos ->
            productoAdapter.setListaProductos(productos)
            usuarioViewModel.getUsuario.observe(viewLifecycleOwner) { usuarios ->
                for (usuario in usuarios) {
                    if (usuario.correo == email.toString()) {
                        rol = usuario.rol.toString()
                        break
                    }
                }
                Log.e("ALgo", rol)
                if (rol == "admin") {
                    binding.btAgregar.visibility = FloatingActionButton.VISIBLE

                }
            }
        }

        binding.btAgregar.setOnClickListener {

            findNavController().navigate(R.id.action_navigation_productos_to_addProductoFragment)
        }
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}