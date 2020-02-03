package net.vinid.moviedb.ui.common.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import net.vinid.moviedb.R

object CustomBindingAdapter {

    @BindingAdapter("app:imageUrl")
    @JvmStatic
    fun imageUrl(imageView: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            Glide.with(imageView.context)
                .load(url)
                .fitCenter()
                .into(imageView)
        }
    }

    @BindingAdapter("app:isFavorite")
    @JvmStatic
    fun setFavoriteStatus(imageView: ImageView, isFavorite: Boolean) {
        if (isFavorite) {
            imageView.setImageResource(R.drawable.ic_favorite_red_48dp)
        } else {
            imageView.setImageResource(R.drawable.ic_favorite_border_white_48dp)
        }
    }

}
