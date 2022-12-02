package com.proyectotienda.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.proyectotienda.databinding.TallasAdminBinding

class TallaAdminAdapter : RecyclerView.Adapter<TallaAdminAdapter.TallaViewHolder>() {


    inner class TallaViewHolder(private val itemBinding: TallasAdminBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

       
        fun bindindItems(list: String?) {
            itemBinding.talla.text = list.toString()
        }


    }

    private var listaProductosCarrito = ArrayList<String?>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TallaViewHolder {

        val itemBinding = TallasAdminBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TallaViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TallaViewHolder, position: Int) {

        val item = listaProductosCarrito[position]
        holder.bindindItems(item)
    }

    override fun getItemCount(): Int {
        return listaProductosCarrito.size
    }

    fun setListaTallas(talla: List<String?>) {
        this.listaProductosCarrito = talla as ArrayList<String?>
        notifyDataSetChanged()

    }




}