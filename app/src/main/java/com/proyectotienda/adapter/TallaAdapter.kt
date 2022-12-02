package com.proyectotienda.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proyectotienda.R
import com.proyectotienda.databinding.CardTallaBinding
import com.proyectotienda.databinding.TallasAdminBinding

class TallaAdapter : RecyclerView.Adapter<TallaAdapter.TallaViewHolder>() {

    private  var clicks : Int = 0
    private  var talla : String = ""
    inner class TallaViewHolder(private val itemBinding: CardTallaBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

       
        fun bindindItems(list: String?) {
            itemBinding.talla.text = list.toString()

            itemBinding.cardTalla.setOnClickListener{

                clicks +=1

                if(clicks == 1){
                    val azul = Color.parseColor("#1261CA")
                    val blanco = Color.WHITE
                    itemBinding.cardTalla.setBackgroundColor(azul)
                    itemBinding.talla.setTextColor(blanco)
                    talla =  itemBinding.talla.text.toString()
                }


                if(clicks >= 2 && talla == itemBinding.talla.text.toString()){
                    val gris = Color.parseColor("#F0F0F2")
                    val negro = Color.BLACK
                    itemBinding.cardTalla.setBackgroundColor(gris)
                    itemBinding.talla.setTextColor(negro)
                    clicks = 0
                    talla = ""
                }
//                Log.e("talla",talla())
            }

        }


    }

    private var listaProductosCarrito = ArrayList<String?>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TallaViewHolder {

        val itemBinding = CardTallaBinding.inflate(
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

    fun talla() :String{
        return talla
    }




}