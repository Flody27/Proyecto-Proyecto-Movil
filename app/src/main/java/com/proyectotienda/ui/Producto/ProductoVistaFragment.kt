package com.proyectotienda.ui.Producto

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.proyectotienda.R
import com.proyectotienda.adapter.TallaAdapter
import com.proyectotienda.adapter.TallaAdminAdapter
import com.proyectotienda.databinding.CardProductoBinding
import com.proyectotienda.databinding.CardTallaBinding
import com.proyectotienda.databinding.FragmentProductoVistaBinding
import com.proyectotienda.model.Carrito
import com.proyectotienda.viewModel.CarritoViewModel


class ProductoVistaFragment : Fragment() {

    private val args by navArgs<ProductoVistaFragmentArgs>()

    private lateinit var carritoViewModel: CarritoViewModel

    private var _binding: FragmentProductoVistaBinding? = null

    private val binding get() = _binding!!

    private var tallas: List<String?> = ArrayList()

    private var talla: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        carritoViewModel =
            ViewModelProvider(this).get(CarritoViewModel::class.java)

        _binding = FragmentProductoVistaBinding.inflate(inflater, container, false)

        binding.tituloProducto.text = args.producto.nombre
        binding.precioProducto.text = args.producto.precio.toString()

        tallas = args.producto.tallas

        binding.btEditarProducto.setOnClickListener {

            val action = ProductoVistaFragmentDirections
                .actionProductoVistaFragmentToUpdateProducto(args.producto)
            findNavController().navigate(action)
        }

        val tallasAdpater = TallaAdapter()
        val recilador = binding.recilcadorTallas
        recilador.adapter = tallasAdpater
        recilador.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        tallasAdpater.setListaTallas(tallas)

        binding.btAgregarCarrito.setOnClickListener {
            talla = tallasAdpater.talla()
            agregarAlcarrito()
        }

        return binding.root
    }

    private fun agregarAlcarrito() {


        if (talla.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "selecione una talla",
                Toast.LENGTH_LONG
            ).show()
        } else {
            val productoID = args.producto.id
            val producto = args.producto.nombre
            val precio = args.producto.precio
            val carrito = Carrito("", productoID, producto, precio, 1, talla)
            carritoViewModel.addCarrito(carrito)
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_carrito_exito),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}