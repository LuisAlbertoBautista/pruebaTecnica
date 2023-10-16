package com.example.pruebatecnicaapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebatecnicaapp.R
import com.example.pruebatecnicaapp.databinding.ItemEventoBinding
import com.example.pruebatecnicaapp.db.Evento

class EventoAdapter(private val eventos: List<Evento>) :
    RecyclerView.Adapter<EventoAdapter.EventoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        return EventoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_evento,parent,false))
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        holder.bind(eventos[position])
    }

    override fun getItemCount() = eventos.size

    inner class EventoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding : ItemEventoBinding
        fun bind(evento: Evento) {
            binding = ItemEventoBinding.bind(itemView)
            binding.nombreTextView.text = evento.nombre
            binding.fechaTextView.text = evento.fecha
            binding.descripcionTextView.text = evento.descripcion
            //binding.statusTextview.text = evento.status
        }
    }


}