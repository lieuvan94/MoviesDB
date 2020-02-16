package net.vinid.moviedb.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.vinid.moviedb.R
import net.vinid.moviedb.data.local.entity.MovieEntity
import net.vinid.moviedb.ui.common.recycleview.MovieDiffCallback

/**
 * Created by Nguyen Van Lieu on 2/3/2020.
 */
class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.BindingHolder>(){
    private var dataList: ArrayList<MovieEntity> = ArrayList()

    var onItemClick: (moviesItem: MovieEntity) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val layoutId = R.layout.item_movies
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

    fun setItems(newListMovie: List<MovieEntity>, layoutManager: RecyclerView.LayoutManager) {
        if (layoutManager is GridLayoutManager) {
            Log.d("TEST", "Adapter, set girdlayout")
            val diffUtil = DiffUtil.calculateDiff(MovieDiffCallback(dataList, newListMovie))
            this.dataList.clear()
            dataList.addAll(newListMovie)
            diffUtil.dispatchUpdatesTo(this)
        } else
            if (layoutManager is LinearLayoutManager) {
                Log.d("TEST", "Adapter, set linear")
                val lastIndex = dataList.size
                dataList.addAll(newListMovie)
                notifyItemInserted(lastIndex)
            }
    }


    fun getItem(position: Int): MovieEntity {
        return dataList[position]
    }

    class BindingHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

}