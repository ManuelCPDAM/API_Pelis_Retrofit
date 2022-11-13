package com.example.api_pelis_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.api_pelis_retrofit.adapter.MovieAdapter
import com.example.api_pelis_retrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private var movieLiveData = MutableLiveData<List<Result>>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareRecyclerView()
        getPopularMovies()
        observeMovieLiveData().observe(this, Observer { movieList ->
            movieAdapter.setMovieList(movieList)
        })
        val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        binding.RecyclerMovies.addItemDecoration(decoration)

    }

    private fun prepareRecyclerView() {
        movieAdapter = MovieAdapter()
        binding.RecyclerMovies.apply {
            layoutManager = GridLayoutManager(applicationContext,1)
            adapter = movieAdapter
        }
    }

    val api : APIService by lazy{
        Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/movie/").addConverterFactory(GsonConverterFactory.create())
            .build().create(APIService::class.java)
    }

    fun getPopularMovies(){
        api.getPupularMovies("829b25b166724dcde4279adce665dbb1").enqueue(object : Callback<MoviesResponse>{
            override fun onResponse(
                call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                if(response.body()!=null){
                    movieLiveData.value = response.body()!!.results
                }
                else{
                    showError()
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                showError()
            }
        })
    }

    fun observeMovieLiveData() : LiveData<List<Result>> {
        return movieLiveData
    }

    private fun showError() {
        Toast.makeText(this,"Ha ocurrido un errro",Toast.LENGTH_SHORT).show()
    }

}