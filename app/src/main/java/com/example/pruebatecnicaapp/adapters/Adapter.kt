package com.example.pruebatecnicaapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebatecnicaapp.R
import com.example.pruebatecnicaapp.models.Model
import com.squareup.picasso.Picasso

class Adapter(val items: List<Model>, val context: Context) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler,parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = items[position].nombre
        Picasso.with(context).load("url").into(holder.imagen)
        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val title = view.findViewById<TextView>(R.id.tv_tittle)
        val imagen = view.findViewById<ImageView>(R.id.img)
    }
}