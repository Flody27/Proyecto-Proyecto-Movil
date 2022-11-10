package com.proyectotienda.repository

import androidx.lifecycle.MutableLiveData
import com.proyectotienda.data.ComprasDao
import com.proyectotienda.model.Compras

class ComprasRepository (private val comprasDao: ComprasDao) {

    fun addCompra(compras: Compras){
        comprasDao.addCompra(compras)
    }

    fun deleteCompra(compras: Compras){
        comprasDao.deleteCompra(compras)
    }

    val getCompra : MutableLiveData<List<Compras>> = comprasDao.getCompra()



}