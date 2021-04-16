package com.example.okcaroule.locationFragment

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.okcaroule.R
import com.example.okcaroule.locationFragment.database.ClientEntity
import com.example.okcaroule.locationFragment.viewModel.MyClientDiffUtil
import java.lang.String.format

class ClientRecyclerAdapter(val context: Activity): RecyclerView.Adapter<ClientRecyclerAdapter.viewHolder>() {
    private var data = emptyList<ClientEntity>()

    fun getItem(position: Int): Long {
        return data[position].clientId
    }
    inner class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val clientName = itemView.findViewById<TextView>(R.id.client_name)
        val clientNumber = itemView.findViewById<TextView>(R.id.bikes_count_tv)
        val bikesCount = itemView.findViewById<TextView>(R.id.phone_number_tv)
        val locationDuration = itemView.findViewById<TextView>(R.id.location_duration_tv)
        val price_tv = itemView.findViewById<TextView>(R.id.price_tv)
        val caution_tv = itemView.findViewById<TextView>(R.id.caution_tv)
        val lock_tv = itemView.findViewById<TextView>(R.id.lock_tv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.location_client_item, parent, false)
        return viewHolder(root)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val element = data[position]
        holder.apply {
            clientName.text = element.clientName
            clientNumber.text = format(context.getString(R.string.phone_text),element.clientPhone )
            locationDuration.text = format(context.getString(R.string.Duree_text),element.locationDuration )
            bikesCount.text = format(context.getString(R.string.bikes_count_text),element.bikesCount.toString() )
            price_tv.text = format(context.getString(R.string.price_text),element.price.toString() )
            caution_tv.text = format(context.getString(R.string.caution_text),element.caution )
            lock_tv.text = format(context.getString(R.string.locks_text),element.locks.toString() )

        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
    fun setData(newList: List<ClientEntity>) {
        val myDiffUtil = MyClientDiffUtil(data, newList)
        val result = DiffUtil.calculateDiff(myDiffUtil)
        data = newList
        result.dispatchUpdatesTo(this)
    }



}