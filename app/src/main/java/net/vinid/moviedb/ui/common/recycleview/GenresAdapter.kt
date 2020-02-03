package net.vinid.moviedb.ui.common.recycleview

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
class GenresAdapter(
    private val dataList: ArrayList<GenresItem>
) : RecyclerView.Adapter<GenresAdapter.BindingHolder>(){

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
        holder.binding.hasPendingBindings()
        holder.binding.executePendingBindings()
        holder.itemView.setOnClickListener {
            onItemClick(data)
        }
    }

    class BindingHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

}