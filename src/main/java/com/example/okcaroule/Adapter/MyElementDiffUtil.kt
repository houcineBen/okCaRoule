package com.example.okcaroule.Adapter

import android.sax.Element
import androidx.recyclerview.widget.DiffUtil
import com.example.okcaroule.commandsFragment.dataBase.ElementsEntity

class MyElementDiffUtil(
    private val newList: List<ElementsEntity>,
    private val oldList: List<ElementsEntity>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].ElementId == newList[newItemPosition].ElementId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].ElementId != newList[newItemPosition].ElementId -> false
            oldList[oldItemPosition].ElementDescription != newList[newItemPosition].ElementDescription -> false
            oldList[oldItemPosition].ElementName != newList[newItemPosition].ElementName-> false
            oldList[oldItemPosition].ElementQuantity != newList[newItemPosition].ElementQuantity -> false
            else -> true
        }
    }
}