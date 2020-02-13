package net.vinid.moviedb.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import net.vinid.moviedb.R

/**
 * Created by Nguyen Van Lieu on 2/3/2020.
 */
class GenresAdapter : RecyclerView.Adapter<GenresAdapter.BindingHolder>(){

    private var dataList: MutableList<GenresItem> = mutableListOf()
    var onItemClick: (genresItem: GenresItem) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val layoutId = R.layout.item_genres
        // DataBinding
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )
        return BindingHolder(binding)
    }

    override fun getItemCount()= dataList.size

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val data = dataList[position]
        holder.binding.setVariable(BR.data, data)
        holder.binding.executePendingBindings()

        holder.itemView.setOnClickListener {
            onItemClick(data)
        }
    }

    fun setItems(genres: List<GenresItem>) {
        val startPosition = this.dataList.size
        this.dataList.addAll(genres)
        notifyItemRangeChanged(startPosition, genres.size)
    }


    fun getItem(position: Int): GenresItem {
        return dataList[position]
    }

    class BindingHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

}