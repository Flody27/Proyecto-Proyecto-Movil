package com.proyectotienda.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.proyectotienda.model.Compras

class ComprasDao {

    private val coleccion1 = "Compras"
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }
    fun addCompra(compras: Compras){

        val documento : DocumentReference

        if (compras.idCompra.isEmpty()){
            documento = firestore
                .collection(coleccion1)
                .document()
            compras.idCompra = documento.id
        }
        else{
            documento = firestore.collection(coleccion1).document(compras.idCompra)
        }
        documento.set(compras)
            .addOnSuccessListener {
                Log.d("addCompra","Compra agregada")
            }
            .addOnCanceledListener {
                Log.e("addCompra", "La compra no fue agregada")
            }

    }

    fun deleteCompra(compras: Compras){


        if (compras.idCompra.isNotEmpty()){
            firestore
                .collection(coleccion1)
                .document(compras.idCompra)
                .delete()
                .addOnSuccessListener {
                    Log.d("deleteCompra","Compra eliminadq")
                }
                .addOnCanceledListener {
                    Log.e("deleteCompra", "La compra no fue eliminada")
                }

        }
    }

    fun getCompra() : MutableLiveData<List<Compras>> {
        val listaProductos = MutableLiveData<List<Compras>>()

        firestore
            .collection(coleccion1)
            .addSnapshotListener{ instantanea, e ->
                if (e != null){
                    return@addSnapshotListener
                }
                if (instantanea != null){
                    val lista = ArrayList<Compras>()

                    instantanea.documents.forEach {
                        val compra = it.toObject(Compras::class.java)
                        if (compra != null) {
                            lista.add(compra)
                        }
                    }
                    listaProductos.value = lista
                }
            }
        return listaProductos
    }

}