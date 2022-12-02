package com.proyectotienda.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.proyectotienda.data.CarritoDao
import com.proyectotienda.model.Carrito
import com.proyectotienda.repository.CarritoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarritoViewModel (application: Application) : AndroidViewModel(application) {

    private val carritoRepository = CarritoRepository(CarritoDao())

    val getCarrito: MutableLiveData<List<Carrito>> = carritoRepository.getCarrito


    fun addCarrito(carrito: Carrito){
        viewModelScope.launch(Dispatchers.IO){
            carritoRepository.addCarrito(carrito)
        }
    }

    fun deleteCarrito(carrito: Carrito){
        viewModelScope.launch(Dispatchers.IO){
            carritoRepository.deleteCarrito(carrito)
        }
    }


}