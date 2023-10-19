package com.example.pruebatecnicaapp.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebatecnicaapp.R
import com.example.pruebatecnicaapp.models.Result
import com.example.pruebatecnicaapp.ui.DetallePokemon
import com.example.pruebatecnicaapp.utils.Constants.BASE_IMAGE
import com.example.pruebatecnicaapp.utils.Constants.TAG
import com.squareup.picasso.Picasso

class PokemonAdapter(val items: List<Result>, val context: Context) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon,parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = items[position].name
        val segmentos = items[position].url.length
        val id = items[position].url.substring(segmentos-2 , segmentos-1)+ ".png"
        Log.d(TAG, "$BASE_IMAGE$id")
        Picasso.with(context).load("$BASE_IMAGE$id").into(holder.imagen)
        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, DetallePokemon::class.java).apply {
                putExtra("id", items[position].url.substring(segmentos-2 , segmentos-1))
            })
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val title = view.findViewById<TextView>(R.id.nombreTextView)
        val imagen = view.findViewById<ImageView>(R.id.img_pokemon)
    }
}