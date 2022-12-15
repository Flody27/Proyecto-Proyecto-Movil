package com.proyectotienda.ui.Producto

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
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

    private lateinit var tallasAdapter: TallaAdminAdapter

    private lateinit var recilador: RecyclerView

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

        _binding = FragmentUpdateProductoBinding.inflate(inflater, container, false)

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

        binding.btDeleteProducto.setOnClickListener {
            eliminarProducto()
        }

        binding.btAgregarTalla.setOnClickListener {
            agregarTalla()
            lista()
            tallasAdapter.setListaTallas(tallas)

        }
        val storageRef = Firebase.storage.reference
        var imageUri = ""
        var imagePickerActivityResult: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result != null) {
                    imageUri = result.data?.data!!.toString()
                    Glide.with(this)
                        .load(imageUri)
                        .into(binding.imageView4)
                }
            }

        binding.btUpdateImage.setOnClickListener {
            if(args.producto.imagen?.isEmpty() == true){
                eliminarImagen()
            }
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            imagePickerActivityResult.launch(galleryIntent)
        }

        binding.btUpdateProducto.setOnClickListener {
            if (imageUri.isEmpty()) {
                actualizarProducto()
            } else {
                val image = getFileName(requireContext(), imageUri.toUri())
                val uploadTask = storageRef.child("rutaNube/$image").putFile(imageUri.toUri())
                uploadTask.addOnSuccessListener {
                    storageRef.child("rutaNube/$image").downloadUrl.addOnSuccessListener {
                        args.producto.imagen = it.toString()
                        actualizarProducto()
                        Log.e("Firebase", "download passed")
                    }.addOnFailureListener {
                        Log.e("Firebase", "Failed in downloading")
                    }
                }.addOnFailureListener {
                    Log.e("Firebase", "Image Upload fail")
                }
            }
        }

        return binding.root
    }

    private fun agregarTalla() {
        if (binding.etTallasProd.text.isNotEmpty()) {
            tallas += binding.etTallasProd.text.toString()
            binding.etTallasProd.setText("")

        } else {
            Toast.makeText(requireContext(), "Campo vacio", Toast.LENGTH_LONG).show()
        }

    }

    private fun lista() {
        recilador.adapter = tallasAdapter
        recilador.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
    }

    private fun eliminarImagen() {
        if (args.producto.imagen?.isNotEmpty() == true) {
            var image = args.producto.imagen?.let { getFileName(requireContext(), it.toUri()) }
            Firebase.storage.reference.child("rutaNube/${image}").delete()
        }
    }

    private fun eliminarProducto() {
        val alerta = AlertDialog.Builder(requireContext())
        alerta.setTitle(R.string.title_eliminar_producto)
        alerta.setMessage(getString(R.string.pregunta_delete_producto) + ":${args.producto.nombre} ?")
        alerta.setPositiveButton(getString(R.string.msg_si)) { _, _ ->
            eliminarImagen()
            productoViewModel.deleteProducto(args.producto)
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_producto_deleted),
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_updateProducto_to_navigation_productos)
        }
        alerta.setNegativeButton(getString(R.string.msg_no)) { _, _ -> }
        alerta.create().show()
    }

    @SuppressLint("Range")
    private fun getFileName(context: Context, uri: Uri): String? {
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                }
            }
        }
        return uri.path?.lastIndexOf('/')?.let { uri.path?.substring(it) }
    }


    private fun actualizarProducto() {
        val nombre = binding.etNombreProd.text.toString()
        if (nombre.isNotEmpty()) {
            val precio = binding.etPrecioProd.text.toString().toInt()
            val producto = Producto(args.producto.id, nombre, precio, args.producto.imagen, tallas)
            productoViewModel.addProducto(producto)
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_producto_updated),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateProducto_to_navigation_productos)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.err_producto_updated),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}