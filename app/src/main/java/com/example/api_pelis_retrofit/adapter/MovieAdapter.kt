package com.example.api_pelis_retrofit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.api_pelis_retrofit.Result
import com.example.api_pelis_retrofit.databinding.ItemMoviesBinding

class MovieAdapter : RecyclerView.Adapter<MovieViewHolder>() {
    private var movieList = ArrayList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMoviesBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500"
        + movieList[position].poster_path)
            .into(holder.binding.ImagenPeli)
        holder.binding.TituloPeli.text = movieList[position].title
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setMovieList(movieList:List<Result>){
        this.movieList = movieList as ArrayList<Result>
        notifyDataSetChanged()
    }

}