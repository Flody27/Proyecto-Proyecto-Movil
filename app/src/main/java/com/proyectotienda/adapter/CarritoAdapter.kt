package com.proyectotienda.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.proyectotienda.databinding.CardItemCarritoBinding
import com.proyectotienda.model.Carrito
import com.proyectotienda.model.Producto
import kotlin.math.log

class CarritoAdapter : RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    var precio = 0

    inner class CarritoViewHolder(private val itemBinding: CardItemCarritoBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bindindItems(carrito: Carrito) {

            itemBinding.tvTituloProducto.text = carrito.producto
            itemBinding.tvTalla.text = carrito.talla
            itemBinding.tvCantidad.text = carrito.cantidad.toString()
            itemBinding.tvPrecio.text = "$${carrito.precio}"
            precio = carrito.preciobase!!
            Glide.with(itemBinding.root.context)
                .load(carrito.imagen)
                .into(itemBinding.imageView3)
            val precioBase = carrito.precio
            if (precioBase != null) {

                itemBinding.btAumentar.setOnClickListener {
                    carrito.cantidad = carrito.cantidad?.plus(1)
                    if (carrito.cantidad!! >= 1) {
                        itemBinding.btDisminuir.visibility = ImageButton.VISIBLE
                    }
                    carrito.precio = precioBase * carrito.cantidad!!
                    itemBinding.tvCantidad.text = carrito.cantidad.toString()
                    itemBinding.tvPrecio.text = "$${carrito.precio}"

                }

                itemBinding.btDisminuir.setOnClickListener {
                    carrito.cantidad = carrito.cantidad?.minus(1)
                    if (carrito.cantidad!! <= 1) {
                        itemBinding.btDisminuir.visibility = ImageButton.INVISIBLE
                    }

                    carrito.precio = precioBase * carrito.cantidad!!
                    itemBinding.tvCantidad.text = carrito.cantidad.toString()
                    itemBinding.tvPrecio.text = "$${carrito.precio}"

                }
            }

        }

    }

    private var listaProductosCarrito = emptyList<Carrito>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {

        val itemBinding = CardItemCarritoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )


        return CarritoViewHolder(itemBinding)
    }


    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {

        val item = listaProductosCarrito[position]
        holder.bindindItems(item)
    }

    override fun getItemCount(): Int {
        return listaProductosCarrito.size
    }

    fun setListaProductos(carrito: List<Carrito>) {
        this.listaProductosCarrito = carrito
        notifyDataSetChanged()
    }



    fun needRefresh() {

        listaProductosCarrito.forEach {
            it.cantidad = 1
            it.precio = precio
        }

    }


}


