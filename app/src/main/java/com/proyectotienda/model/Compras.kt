package com.proyectotienda.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Compras(

    var idCompra: String,
    val montoTotal: Int?,
    val fechaCompras: String?,
    val productoId: List<String?>
    ) : Parcelable {

    constructor() :
            this("",
                0,
                "",
                listOf()
            )



}