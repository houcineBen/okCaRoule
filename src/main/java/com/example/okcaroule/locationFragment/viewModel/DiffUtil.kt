package com.example.okcaroule.locationFragment.viewModel

import androidx.recyclerview.widget.DiffUtil
import com.example.okcaroule.locationFragment.database.ClientEntity

class MyClientDiffUtil(
    private val oldList: List<ClientEntity>,
    private val newList: List<ClientEntity>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].clientId != newList[newItemPosition].clientId -> false
            oldList[oldItemPosition].clientName != newList[newItemPosition].clientName -> false
            oldList[oldItemPosition].clientPhone != newList[newItemPosition].clientPhone -> false
            oldList[oldItemPosition].bikesCount != newList[newItemPosition].bikesCount -> false
            oldList[oldItemPosition].caution != newList[newItemPosition].caution -> false
            oldList[oldItemPosition].locationDuration != newList[newItemPosition].locationDuration -> false
            oldList[oldItemPosition].locks != newList[newItemPosition].locks -> false
            oldList[oldItemPosition].price != newList[newItemPosition].price -> false
            else -> true
        }
    }

}