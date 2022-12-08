package com.proyectotienda.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Producto(

    var id: String,
    val nombre: String?,
    val precio: Int?,
    val imagen: String?,
    var tallas: List<String?>,
    ) : Parcelable{

        constructor() :
                    this("","",0,"", listOf())
    }





