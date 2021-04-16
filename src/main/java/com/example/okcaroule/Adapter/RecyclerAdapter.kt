package com.example.okcaroule.Adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.example.okcaroule.CommandsViewModel
import com.example.okcaroule.R
import com.example.okcaroule.commandsFragment.dataBase.ElementsEntity


class RecyclerAdapter(val dialog: MaterialDialog, val viewModel: CommandsViewModel): RecyclerView.Adapter<RecyclerAdapter.viewHolder>() {
    var data = listOf<ElementsEntity>()

    fun getItem(position: Int): ElementsEntity {
        return data[position]
    }
    inner class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTv = itemView.findViewById<TextView>(R.id.name_tv)
        val quantityTv = itemView.findViewById<TextView>(R.id.quantity_tv)
        val descriptionTv = itemView.findViewById<TextView>(R.id.description_tv)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.viewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return viewHolder(item)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.viewHolder, position: Int) {

        var pos = data[position]
        holder.apply {
            nameTv.text = pos.ElementName
            quantityTv.text = pos.ElementQuantity.toString()
            descriptionTv.text = pos.ElementDescription
            itemView.setOnClickListener {
                dialog.show {
                    var editName = findViewById<EditText>(R.id.edit_name)
                    var editQuantity = findViewById<EditText>(R.id.edit_quantity)
                    var editDescription = findViewById<EditText>(R.id.edit_discription)
                    val applyBtn = findViewById<Button>(R.id.apply_btn)
                    val cancelBtn = findViewById<Button>(R.id.cancel_btn)
                    editName.text = nameTv.text.getEditable()
                    editQuantity.text = quantityTv.text.getEditable()
                    editDescription.text = descriptionTv.text.getEditable()
                    applyBtn.setOnClickListener {
                        nameTv.text = editName.text.toString()
                        quantityTv.text = editQuantity.text.toString()
                        descriptionTv.text = editDescription.text.toString()
                        pos.ElementDescription = editDescription.text.toString()
                        pos.ElementName = editName.text.toString()
                        pos.ElementQuantity = editQuantity.text.toString().toInt()
                        viewModel.update(data[position])
                        this.dismiss()
                    }
                    cancelBtn.setOnClickListener {
                        this.dismiss()
                    }


                }


            }
        }

    }
    fun CharSequence.getEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    override fun getItemCount(): Int {
        return data.size
    }
    fun setElement(newList: List<ElementsEntity>) {
        val myDiffUtil = MyElementDiffUtil(newList, data)
        val result = DiffUtil.calculateDiff(myDiffUtil)
        data = newList
        result.dispatchUpdatesTo(this)
    }
}