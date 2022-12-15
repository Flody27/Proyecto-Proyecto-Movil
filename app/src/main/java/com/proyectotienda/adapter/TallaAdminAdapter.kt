package com.proyectotienda.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.proyectotienda.databinding.TallasAdminBinding


class TallaAdminAdapter : RecyclerView.Adapter<TallaAdminAdapter.TallaViewHolder>() {

    private  var clicks : Int = 0
    private  var talla : String = ""
    inner class TallaViewHolder(private val itemBinding: TallasAdminBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

       
        fun bindindItems(list: String?) {
            itemBinding.talla.text = list.toString()




            itemBinding.tallasAdmin.setOnClickListener{

                clicks +=1
                if(clicks == 2){
                    talla = itemBinding.talla.text.toString()
                    eliminar(itemBinding.talla.text.toString())
                    itemBinding.tallasAdmin.visibility = CardView.INVISIBLE
                    clicks = 0
                }

            }

        }


    }



    private fun eliminar(talla: String) {
        if(talla.isNotEmpty()){
            listaProductosCarrito.remove(talla)
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




