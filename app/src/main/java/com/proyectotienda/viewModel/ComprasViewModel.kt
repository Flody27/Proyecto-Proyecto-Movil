package com.proyectotienda.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.proyectotienda.data.ComprasDao
import com.proyectotienda.model.Compras
import com.proyectotienda.repository.ComprasRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ComprasViewModel (application: Application) : AndroidViewModel(application) {

    private val comprasRepository: ComprasRepository = ComprasRepository(ComprasDao())

    val getCompra: MutableLiveData<List<Compras>> = comprasRepository.getCompra


    fun addCompra(compras: Compras){
        viewModelScope.launch(Dispatchers.IO){
            comprasRepository.addCompra(compras)
        }
    }

    fun deleteCompra(compras: Compras){
        viewModelScope.launch(Dispatchers.IO){
            comprasRepository.deleteCompra(compras)
        }
    }

}