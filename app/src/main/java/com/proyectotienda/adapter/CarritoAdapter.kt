package com.proyectotienda.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proyectotienda.databinding.CardItemCarritoBinding
import com.proyectotienda.model.Carrito

class CarritoAdapter : RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    inner class CarritoViewHolder(private val itemBinding: CardItemCarritoBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bindindItems(carrito: Carrito) {
            var precio: Int? = carrito.precio
            itemBinding.tvTituloProducto.text = carrito.producto
            itemBinding.tvTalla.text = carrito.talla
            itemBinding.tvCantidad.text = carrito.cantidad.toString()
            itemBinding.tvPrecio.text = "$${carrito.precio}"

            var cantidad: Int? = 1

            if (cantidad != null) {

                itemBinding.btAumentar.setOnClickListener {
                    cantidad += 1
                    if (cantidad >= 1) {
                        itemBinding.btDisminuir.isEnabled = true
                    }
                   itemBinding.tvCantidad.text = cantidad.toString()
                    carrito.cantidad = cantidad
                    precio = carrito.precio?.times(carrito.cantidad!!)

                    Log.e("Precio","${carrito.precio} ${carrito.cantidad}")
                }

                itemBinding.btDisminuir.setOnClickListener {
                    cantidad -= 1
                    if (cantidad <= 1) {
                        itemBinding.btDisminuir.isEnabled = false
                    }
                    itemBinding.tvCantidad.text = cantidad.toString()


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


}