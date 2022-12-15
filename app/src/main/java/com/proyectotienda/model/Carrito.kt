package com.proyectotienda.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Carrito (

    var idCarrito: String,
    val productoId: String?,
    val producto: String?,
    var precio: Int?,
    var preciobase: Int?,
    var cantidad: Int?,
    var imagen: String?,
    val talla: String?
    ) : Parcelable {

    constructor() : this("",
                "",
                "",
                0,
                0,
                0,
                "",
                "")

}