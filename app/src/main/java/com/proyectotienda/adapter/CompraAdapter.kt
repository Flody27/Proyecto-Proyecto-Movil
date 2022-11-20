package com.proyectotienda.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.proyectotienda.databinding.CardItemCarritoBinding
import com.proyectotienda.databinding.CardProductoBinding
import com.proyectotienda.model.Producto

class CompraAdapter : RecyclerView.Adapter<CompraAdapter.CompraViewHolder>() {

    inner class CompraViewHolder(private val itemBinding: CardItemCarritoBinding) :
    RecyclerView.ViewHolder(itemBinding.root)    {

        fun bindindItems(producto: Producto){

        }

    }

    private var listaProductosCarrito = emptyList<Producto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompraViewHolder {

        val itemBinding = CardItemCarritoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return CompraViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CompraViewHolder, position: Int) {

        val lugar = listaProductosCarrito[position]
        holder.bindindItems(lugar)
    }

    override fun getItemCount(): Int {
        return listaProductosCarrito.size
    }

    fun setListaProductos(productos: List<Producto>)
    {
        this.listaProductosCarrito = productos
        notifyDataSetChanged()
    }

}