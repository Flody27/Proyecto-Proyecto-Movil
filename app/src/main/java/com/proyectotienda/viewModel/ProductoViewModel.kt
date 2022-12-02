package com.proyectotienda.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.proyectotienda.data.ProductoDao
import com.proyectotienda.model.Producto
import com.proyectotienda.repository.ProductoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProductoViewModel(application: Application) : AndroidViewModel(application) {

    private val productoRepository: ProductoRepository = ProductoRepository(ProductoDao())

    val getProductos: MutableLiveData<List<Producto>> = productoRepository.getProductos

    fun addProducto(producto: Producto){
        viewModelScope.launch(Dispatchers.IO){
            productoRepository.addProducto(producto)
        }
    }

    fun deleteProducto(producto: Producto){
        viewModelScope.launch(Dispatchers.IO){
            productoRepository.deleteProducto(producto)
        }
    }


}