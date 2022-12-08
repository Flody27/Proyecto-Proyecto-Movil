package com.proyectotienda.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.proyectotienda.databinding.CardItemCarritoBinding
import com.proyectotienda.model.Carrito

class CarritoAdapter : RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    inner class CarritoViewHolder(private val itemBinding: CardItemCarritoBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bindindItems(carrito: Carrito) {

            itemBinding.tvTituloProducto.text = carrito.producto
            itemBinding.tvTalla.text = carrito.talla
            itemBinding.tvCantidad.text = carrito.cantidad.toString()
            itemBinding.tvPrecio.text = "$${carrito.precio}"

            Glide.with(itemBinding.root.context)
                .load(carrito.imagen)

                .into(itemBinding.imageView3)

            val precioBase = carrito.precio

            if (precioBase != null) {

                itemBinding.btAumentar.setOnClickListener {
                    carrito.cantidad = carrito.cantidad?.plus(1)
                    if (carrito.cantidad!! >= 1) {
                        itemBinding.btDisminuir.isEnabled = true
                    }

                    carrito.precio =  precioBase * carrito.cantidad!!
                    itemBinding.tvCantidad.text = carrito.cantidad.toString()
                    itemBinding.tvPrecio.text = "$${carrito.precio}"
                    Log.e("Precio","${carrito.precio} , ${carrito.cantidad}")
                }

                itemBinding.btDisminuir.setOnClickListener {
                    carrito.cantidad = carrito.cantidad?.minus(1)

                    if (carrito.cantidad!! <= 1) {
                        itemBinding.btDisminuir.isEnabled = false
                    }

                    carrito.precio =  precioBase * carrito.cantidad!!
                    itemBinding.tvCantidad.text = carrito.cantidad.toString()
                    itemBinding.tvPrecio.text = "$${carrito.precio}"
                    Log.e("Precio","${carrito.precio} , ${carrito.cantidad}")
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


