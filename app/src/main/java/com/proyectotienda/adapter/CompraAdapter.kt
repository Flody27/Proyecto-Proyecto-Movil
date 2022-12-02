package com.proyectotienda.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.proyectotienda.databinding.CardHistorialComprasBinding
import com.proyectotienda.databinding.CardItemCarritoBinding
import com.proyectotienda.model.Compras
import com.proyectotienda.model.Producto
import com.proyectotienda.ui.Producto.ProductoVistaFragmentArgs


class CompraAdapter : RecyclerView.Adapter<CompraAdapter.CompraViewHolder>() {

    inner class CompraViewHolder(private val itemBinding:CardHistorialComprasBinding) :
    RecyclerView.ViewHolder(itemBinding.root)    {

        fun bindindItems(compras: Compras){
            itemBinding.idCompra.text = compras.idCompra
            itemBinding.fecha.text =  compras.fechaCompras
            itemBinding.total.text = compras.montoTotal.toString()
        }

    }

    private var listaHistorialCompras = emptyList<Compras>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompraViewHolder {

        val itemBinding = CardHistorialComprasBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return CompraViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CompraViewHolder, position: Int) {

        val compra = listaHistorialCompras[position]
        holder.bindindItems(compra)
    }

    override fun getItemCount(): Int {
        return listaHistorialCompras.size
    }

    fun setListaCompras(compras: List<Compras>)
    {
        this.listaHistorialCompras = compras
        notifyDataSetChanged()
    }

}