package com.proyectotienda.repository

import androidx.lifecycle.MutableLiveData
import com.proyectotienda.data.ProductoDao
import com.proyectotienda.model.Producto


class ProductoRepository(private val productoDao: ProductoDao) {

    fun addProducto(producto: Producto){
        productoDao.addProducto(producto)
    }

    fun deleteProducto(producto: Producto){
        productoDao.deleteProducto(producto)
    }


    val getProductos : MutableLiveData<List<Producto>> = productoDao.getProductos()





}