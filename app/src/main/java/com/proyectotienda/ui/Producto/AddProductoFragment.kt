package com.proyectotienda.ui.Producto

import android.annotation.SuppressLint
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
import com.proyectotienda.R
import com.proyectotienda.databinding.FragmentAddProductoBinding
import com.proyectotienda.model.Producto
import com.proyectotienda.viewModel.ProductoViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.proyectotienda.adapter.TallaAdminAdapter
import com.proyectotienda.utilities.ImagenUtiles

class AddProductoFragment : Fragment() {

    private lateinit var productoViewModel: ProductoViewModel

    private var _binding: FragmentAddProductoBinding? = null
    private val binding get() = _binding!!
    private lateinit var producto: Producto

    private var imagen: String? = ""

    private var tallas: List<String?> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productoViewModel =
            ViewModelProvider(this).get(ProductoViewModel::class.java)

        producto = Producto("", "", 0, "", ArrayList())

        _binding = FragmentAddProductoBinding.inflate(inflater, container, false)

        binding.btAgregarTalla.setOnClickListener {
            agregarTalla()
            val tallasAdpater = TallaAdminAdapter()
            val recilador = binding.RecicladorTallas
            recilador.adapter = tallasAdpater
            recilador.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            tallasAdpater.setListaTallas(tallas)

        }
        val storageRef = Firebase.storage.reference;
        var imageUri = ""
        var imagePickerActivityResult: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result != null) {
                    imageUri = result.data?.data.toString()
                    Glide.with(this)
                        .load(imageUri.toUri())
                        .into(binding.imageView4)
                }
            }

        binding.btAddImage.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            imagePickerActivityResult.launch(galleryIntent)
        }

        binding.btAddProducto.setOnClickListener {
            if (imageUri.isEmpty()) {
                addProducto()
            } else {
                val image = getFileName(requireContext(), imageUri.toUri())
                val uploadTask = storageRef.child("rutaNube/$image").putFile(imageUri.toUri())
                uploadTask.addOnSuccessListener {
                    storageRef.child("rutaNube/$image").downloadUrl.addOnSuccessListener {
                        imagen = it.toString()
                        addProducto()
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

    private fun addProducto() {
        val nombreProducto = binding.etNombreProd.text.toString()
        if (nombreProducto.isNotEmpty()) {
            val precioProducto = binding.etPrecioProd.text.toString().toInt()
            producto = Producto("", nombreProducto, precioProducto, imagen, tallas)
            productoViewModel.addProducto(producto)
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_add_producto),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_addProductoFragment_to_navigation_productos)
        } else {
            Toast.makeText(requireContext(), getString(R.string.msg_data), Toast.LENGTH_LONG).show()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}




