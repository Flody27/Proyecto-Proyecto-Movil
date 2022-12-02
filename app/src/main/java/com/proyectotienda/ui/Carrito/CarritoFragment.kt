package com.proyectotienda.ui.Carrito

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.proyectotienda.R
import com.proyectotienda.adapter.CarritoAdapter
import com.proyectotienda.databinding.FragmentCarritoBinding
import com.proyectotienda.model.Compras
import com.proyectotienda.viewModel.CarritoViewModel
import com.proyectotienda.viewModel.ComprasViewModel
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


class CarritoFragment : Fragment() {

    private var _binding: FragmentCarritoBinding? = null

    private lateinit var carritoViewModel: CarritoViewModel

    private lateinit var comprasViewModel: ComprasViewModel

    private lateinit var carritoAdapter: CarritoAdapter

    private lateinit var reciclador: RecyclerView

    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        carritoViewModel =
            ViewModelProvider(this)[CarritoViewModel::class.java]

        comprasViewModel =
            ViewModelProvider(this)[ComprasViewModel::class.java]

        _binding = FragmentCarritoBinding.inflate(inflater, container, false)

        carritoAdapter = CarritoAdapter()
        reciclador = binding.recicladorCarrito
        reciclador.adapter = carritoAdapter
        reciclador.layoutManager = LinearLayoutManager(requireContext())
        var precio:Int? = 0
        carritoViewModel.getCarrito.observe(viewLifecycleOwner) { carrito ->
            carritoAdapter.setListaProductos(carrito)

            if (carrito.isEmpty()) {
                binding.btRealizarCompra.isEnabled = false
                val gris = Color.parseColor("#BDBDBF")
                binding.btRealizarCompra.setBackgroundColor(gris)
                binding.vacio.isVisible = true
            }

            if (precio != null) {
                carrito.forEach {
                    precio+= it.precio!!
                }
            }
            binding.precioTotal.text = "$${precio}"

        }

        binding.btVaciarCarrito.setOnClickListener {
            vaciarCarrito()
        }

        binding.btRealizarCompra.setOnClickListener {
            realizarCompra()
        }

        return binding.root
    }

    private fun realizarCompra() {
        var compras: Compras
        var idProductos: List<String?> = ArrayList()
        var montoTotal: Int? = 0
        val fecha = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
        carritoViewModel.getCarrito.observe(viewLifecycleOwner) { productos ->
            carritoAdapter.setListaProductos(productos)
            if (montoTotal != null) {
                for (producto in productos) {
                    idProductos += producto.productoId
                    montoTotal += producto.precio!!

                }
            }
        }
        compras = Compras("", montoTotal, fecha, idProductos)
        comprasViewModel.addCompra(compras)
        Toast.makeText(
            requireContext(),
            getString(R.string.msg_compra_realizada),
            Toast.LENGTH_SHORT
        ).show()
        vaciar()

    }


    private fun vaciar() {
        carritoViewModel.getCarrito.observe(viewLifecycleOwner) { productos ->
            carritoAdapter.setListaProductos(productos)
            for (producto in productos) {
                carritoViewModel.deleteCarrito(producto)
            }
        }
    }

    private fun vaciarCarrito() {
        val alerta = AlertDialog.Builder(requireContext())
        alerta.setTitle(R.string.vaciar_carrito)
        alerta.setMessage(getString(R.string.pregunta_vaciar_carrito))
        alerta.setPositiveButton(getString(R.string.msg_si))
        { _, _ ->
            vaciar()
        }
        alerta.setNegativeButton(getString(R.string.msg_no)) { _, _ -> }
        alerta.create().show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}