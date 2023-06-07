package com.example.lessonretrofit
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lessonretrofit.db.MovieEntity


class AdapterHistory : RecyclerView.Adapter<AdapterHistory.MyViewHolderHistory>() {

    var dataHistory = listOf<MovieEntity>()

    class MyViewHolderHistory(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(property: MovieEntity) {
            val title = view.findViewById<TextView>(R.id.movie_title_val)
            val id = view.findViewById<TextView>(R.id.movie_id_val)

            id.text = property.id.toString()
            title.text = property.title
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolderHistory {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.for_recycler_view_history, parent, false)
        return MyViewHolderHistory(v)
    }


    override fun getItemCount(): Int {
        return dataHistory.size
    }

    override fun onBindViewHolder(holder: MyViewHolderHistory, position: Int) {
        holder.bind(dataHistory[position])
    }
}
