package com.proyectotienda.ui.Configuracion

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.proyectotienda.R
import com.proyectotienda.adapter.CompraAdapter
import com.proyectotienda.databinding.FragmentConfiguracionBinding
import com.proyectotienda.viewModel.ComprasViewModel

class ConfiguracionFragment : Fragment() {

    private var _binding: FragmentConfiguracionBinding? = null

    private lateinit var comprasViewModel: ComprasViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         comprasViewModel =
            ViewModelProvider(this).get(ComprasViewModel::class.java)

        _binding = FragmentConfiguracionBinding.inflate(inflater, container, false)

        val comprasAdpater = CompraAdapter()
        val recilador = binding.reciclador
        recilador.adapter = comprasAdpater
        recilador.layoutManager = LinearLayoutManager(requireContext())

        comprasViewModel.getCompra.observe(viewLifecycleOwner){
            compras -> comprasAdpater.setListaCompras(compras)
        }



        val usuario = Firebase.auth.currentUser
        val nombreUsuario = usuario?.displayName
        val correo = usuario?.email
        var foto = usuario?.photoUrl.toString()

        Glide.with(this)
            .load(R.drawable.logo)
            .circleCrop()
            .placeholder(R.drawable.logo)
            .into(binding.imageView2)
        binding.usuario.text = correo

        if(foto.isNotEmpty()){
            Glide.with(this)
                .load(foto)
                .circleCrop()
                .into(binding.imageView2)
            binding.usuario.text = nombreUsuario
        }

        return binding.root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

