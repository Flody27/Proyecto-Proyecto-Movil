package com.proyectotienda.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.proyectotienda.model.Usuario

class UsuarioDao {

    private val coleccion1 = "Usuario"
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }
    fun addUsuario(usuario: Usuario){

        val documento : DocumentReference

        if (usuario.idUsuario.isEmpty()){
            documento = firestore
                .collection(coleccion1)
                .document()
            usuario.idUsuario = documento.id
        }
        else{
            documento = firestore.collection(coleccion1)
                .document(usuario.idUsuario)
        }
        documento.set(usuario)
            .addOnSuccessListener {
                Log.d("AgregarUsuarioo","Usuario agregado")
            }
            .addOnCanceledListener {
                Log.e("AgregarUsuario", "Error  agregando usuario")
            }

    }

    fun deleteUsuario(usuario: Usuario){
        
        if (usuario.idUsuario.isNotEmpty()){
            firestore
                .collection(coleccion1)
                .document(usuario.idUsuario)
                .delete()
                .addOnSuccessListener {
                    Log.d("eliminarUsuario","Eliminar Usuario")
                }
                .addOnCanceledListener {
                    Log.e("eliminarUsuario", "Error eliminado usuario")
                }

        }
    }

    fun getUsuario() : MutableLiveData<List<Usuario>> {
        val listaUsuario = MutableLiveData<List<Usuario>>()
        firestore
            .collection(coleccion1)
            .addSnapshotListener{ instantanea, e ->
                if (e != null){
                    return@addSnapshotListener
                }
                if (instantanea != null){
                    val lista = ArrayList<Usuario>()

                    instantanea.documents.forEach {
                        val usuario = it.toObject(Usuario::class.java)
                        if (usuario != null) {
                            lista.add(usuario)
                        }
                    }
                    listaUsuario.value = lista
                }
            }
        return listaUsuario
    }

}