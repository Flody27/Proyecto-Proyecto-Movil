package com.proyectotienda.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Usuario(
    var idUsuario: String,
    val correo: String?,
    val rol : String?
): Parcelable {
    constructor() : this("","","")
}
