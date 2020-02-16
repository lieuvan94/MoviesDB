package net.vinid.moviedb.ui.common.recycleview

import androidx.recyclerview.widget.DiffUtil
import net.vinid.moviedb.data.local.entity.MovieEntity

class MovieDiffCallback(private val oldList: List<MovieEntity>, private val newList: List<MovieEntity>)
    : DiffUtil.Callback(){

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList.get(oldItemPosition).title!!.equals(newList.get(newItemPosition).title)
    }
}