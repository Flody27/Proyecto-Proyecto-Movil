package com.proyectotienda.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Compras (

    var idCompra: String,
    val montoTotal: String?,
    val fechaCompras: String?,
    val productoId: String?,
    ) : Parcelable {

    constructor() :
            this("",
                "",
                "",
                "")

}