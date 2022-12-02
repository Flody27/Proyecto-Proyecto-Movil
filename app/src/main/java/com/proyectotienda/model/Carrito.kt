package com.proyectotienda.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Carrito (

    var idCarrito: String,
    val productoId: String?,
    val producto: String?,
    var precio: Int?,
    var cantidad: Int?,
    val talla: String?
    ) : Parcelable {

    constructor() :
            this("",
                "",
                "",
                0,
                0,"")

}