package com.proyectotienda.ui.Configuracion

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.proyectotienda.adapter.CompraAdapter
import com.proyectotienda.databinding.FragmentConfiguracionBinding
import com.proyectotienda.viewModel.ComprasViewModel


class ConfiguracionFragment : Fragment() {

    private var _binding: FragmentConfiguracionBinding? = null

    private lateinit var comprasViewModel: ComprasViewModel

    private val binding get() = _binding!!

    @SuppressLint("UseCompatLoadingForDrawables")
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
        val foto = usuario?.photoUrl.toString()
        val correo = usuario?.email
        val image = resources.getDrawable(com.proyectotienda.R.drawable.logo)
//        Log.e("preuba","Correo: ${Firebase.auth.currentUser?.email} usuario $nombreUsuario foto $foto")
        binding.usuario.text = correo

        Glide.with(this)
            .load(image)
            .circleCrop()
            .into(binding.imageView2)

        if(!nombreUsuario.isNullOrEmpty()){
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

