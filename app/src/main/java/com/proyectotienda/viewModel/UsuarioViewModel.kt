package com.proyectotienda.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.proyectotienda.data.UsuarioDao
import com.proyectotienda.model.Usuario
import com.proyectotienda.repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsuarioViewModel (application: Application) : AndroidViewModel(application) {

    private val usuarioRepository = UsuarioRepository(UsuarioDao())

    val getUsuario: MutableLiveData<List<Usuario>> = usuarioRepository.getUsuario


    fun addUsuario(usuario: Usuario){
        viewModelScope.launch(Dispatchers.IO){
            usuarioRepository.addUsuario(usuario)
        }
    }

    fun deleteUsuario(usuario: Usuario){
        viewModelScope.launch(Dispatchers.IO){
            usuarioRepository.deleteUsuario(usuario)
        }
    }


}