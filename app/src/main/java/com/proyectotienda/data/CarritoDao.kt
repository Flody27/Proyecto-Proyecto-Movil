package com.proyectotienda.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import com.proyectotienda.model.Carrito


class CarritoDao {

    private val coleccion1 = "Carrito"
    private val coleccion2 = "MiCarrito"
    private val usuario =  Firebase.auth.currentUser?.email.toString()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }
    fun addCarrito(carrito: Carrito){

        val documento : DocumentReference

        if (carrito.idCarrito.isEmpty()){
            documento = firestore
                .collection(coleccion1)
                .document(usuario).collection(coleccion2)
                .document()
            carrito.idCarrito = documento.id
        }
        else{
            documento = firestore.collection(coleccion1)
                .document(usuario).collection(coleccion2)
                .document(carrito.idCarrito)
        }
        documento.set(carrito)
            .addOnSuccessListener {
                Log.d("AgregarCarrito","Agregado al carrito")
            }
            .addOnCanceledListener {
                Log.e("AgregarCarrito", "Error al agregr al carrito")
            }

    }

    fun deleteCarrito(carrito: Carrito){


        if (carrito.idCarrito.isNotEmpty()){
            firestore
                .collection(coleccion1)
                .document(usuario).collection(coleccion2)
                .document(carrito.idCarrito)
                .delete()
                .addOnSuccessListener {
                    Log.d("eliminarCarrito","Eliminado del carrito")
                }
                .addOnCanceledListener {
                    Log.e("eliminarCarrito", "Error Eliminado del carrito")
                }

        }
    }

    fun getCarrito() : MutableLiveData<List<Carrito>> {
        val listaCarrito = MutableLiveData<List<Carrito>>()

        firestore
            .collection(coleccion1)
            .document(usuario).collection(coleccion2)
            .addSnapshotListener{ instantanea, e ->
                if (e != null){
                    return@addSnapshotListener
                }
                if (instantanea != null){
                    val lista = ArrayList<Carrito>()

                    instantanea.documents.forEach {
                        val carrito = it.toObject(Carrito::class.java)
                        if (carrito != null) {
                            lista.add(carrito)
                        }
                    }
                    listaCarrito.value = lista
                }
            }
        return listaCarrito
    }

}