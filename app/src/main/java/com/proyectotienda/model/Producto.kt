package com.proyectotienda.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Producto(

    var id: String,
    val nombre: String?,
    val precio: Double,
    val colores: String?,
    val tallas: String?,
    ) : Parcelable{

        constructor() :
                    this("","",0.0,"","")
    }





