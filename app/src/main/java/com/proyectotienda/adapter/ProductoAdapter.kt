package com.proyectotienda.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.proyectotienda.databinding.CardProductoBinding
import com.proyectotienda.model.Producto

class ProductoAdapter : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(private val itemBinding: CardProductoBinding) :
    RecyclerView.ViewHolder(itemBinding.root)    {

        fun bindindItems(producto: Producto){

        }

    }

    private var listaProductos = emptyList<Producto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {

        val itemBinding = CardProductoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ProductoViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {

        val lugar = listaProductos[position]
        holder.bindindItems(lugar)
    }

    override fun getItemCount(): Int {
        return listaProductos.size
    }

    fun setListaProductos(productos: List<Producto>)
    {
        this.listaProductos = productos
        notifyDataSetChanged()
    }

}