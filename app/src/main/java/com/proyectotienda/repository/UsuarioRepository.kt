package com.proyectotienda.repository

import androidx.lifecycle.MutableLiveData
import com.proyectotienda.data.UsuarioDao
import com.proyectotienda.model.Usuario


class UsuarioRepository (private val usuarioDao: UsuarioDao) {

    fun addUsuario(usuario: Usuario){
        usuarioDao.addUsuario(usuario)
    }

    fun deleteUsuario(usuario: Usuario){
        usuarioDao.deleteUsuario(usuario)
    }

    val getUsuario : MutableLiveData<List<Usuario>> = usuarioDao.getUsuario()



}