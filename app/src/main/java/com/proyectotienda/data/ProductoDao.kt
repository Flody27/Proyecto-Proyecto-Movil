package com.proyectotienda.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.proyectotienda.model.Producto
import com.google.firebase.firestore.FirebaseFirestoreSettings

class ProductoDao {

    private val coleccion1 = "Productos"
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }
    fun addProducto(producto: Producto){

        val documento : DocumentReference

        if (producto.id.isEmpty()){
            documento = firestore
                .collection(coleccion1)
                .document()
            producto.id = documento.id
        }
        else{
            documento = firestore.collection(coleccion1).document(producto.id)
        }
        documento.set(producto)
            .addOnSuccessListener {
                Log.d("addProducto","Producto agregado")
            }
            .addOnCanceledListener {
                Log.e("addProducto", "El producto no fue agregado")
            }

    }

     fun deleteProducto(producto: Producto){


        if (producto.id.isNotEmpty()){
            firestore
                .collection(coleccion1)
                .document(producto.id)
                .delete()
                .addOnSuccessListener {
                    Log.d("deleteProducto","Producto eliminado")
                }
                .addOnCanceledListener {
                    Log.e("deleteProducto", "El Producto no fue eliminado")
                }

        }
    }

    fun getProductos() : MutableLiveData<List<Producto>> {
        val listaProductos = MutableLiveData<List<Producto>>()

        firestore
            .collection(coleccion1)
            .addSnapshotListener{ instantanea, e ->
                if (e != null){
                    return@addSnapshotListener
                }
                if (instantanea != null){
                    val lista = ArrayList<Producto>()

                    instantanea.documents.forEach {
                        val producto = it.toObject(Producto::class.java)
                        if (producto != null) {
                            lista.add(producto)
                        }
                    }
                    listaProductos.value = lista
                }
            }
        return listaProductos
    }
}