package com.example.pruebatecnicaapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebatecnicaapp.R
import com.example.pruebatecnicaapp.models.Result
import com.example.pruebatecnicaapp.ui.DetallePeliculas
import com.example.pruebatecnicaapp.utils.Constants.BASE_IMAGE
import com.squareup.picasso.Picasso

class PeliculasAdapter(val items: List<Result>, val context: Context) : RecyclerView.Adapter<PeliculasAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pelicula,parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = items[position].original_title
        holder.date.text = items[position].release_date
        Picasso.with(context).load(BASE_IMAGE+items[position].poster_path).into(holder.poster)
        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, DetallePeliculas::class.java).apply {
                putExtra("PELICULA", items[position])
            })
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val title = view.findViewById<TextView>(R.id.tittle)
        val date = view.findViewById<TextView>(R.id.date)
        val poster = view.findViewById<ImageView>(R.id.poster)
    }
}