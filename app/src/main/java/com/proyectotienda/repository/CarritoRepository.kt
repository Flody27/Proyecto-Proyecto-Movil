package com.proyectotienda.repository

import androidx.lifecycle.MutableLiveData
import com.proyectotienda.data.CarritoDao
import com.proyectotienda.model.Carrito


class CarritoRepository (private val carritoDao: CarritoDao) {

    fun addCarrito(carrito: Carrito){
        carritoDao.addCarrito(carrito)
    }

    fun deleteCarrito(carrito: Carrito){
        carritoDao.deleteCarrito(carrito)
    }

    val getCarrito : MutableLiveData<List<Carrito>> = carritoDao.getCarrito()



}