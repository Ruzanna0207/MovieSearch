package com.example.lessonretrofit
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.lessonretrofit.data.Poster

class PosterAdapter : RecyclerView.Adapter<PosterAdapter.PosterViewHolder>() {

    var data = listOf<Poster?>()

    class PosterViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(property: Poster?) {
            val imageView = view.findViewById<ImageView>(R.id.first_poster)

            Glide.with(imageView)
                .load(Uri.parse("https://image.tmdb.org/t/p/original${property?.filePath}"))
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(ColorDrawable(Color.LTGRAY))
                .into(imageView)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterAdapter.PosterViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.for_resycler_view_list, parent, false)
        return PosterAdapter.PosterViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PosterAdapter.PosterViewHolder, position: Int) {
        holder.bind(data[position])
    }
}
