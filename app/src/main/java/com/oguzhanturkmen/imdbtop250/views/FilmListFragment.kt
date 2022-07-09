package com.oguzhanturkmen.imdbtop250.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.oguzhanturkmen.imdbtop250.R
import com.oguzhanturkmen.imdbtop250.adapter.FilmAdapter
import com.oguzhanturkmen.imdbtop250.models.Model
import com.oguzhanturkmen.imdbtop250.viewmdeols.FilmListViewModel
import kotlinx.android.synthetic.main.fragment_film_list.*
import kotlinx.coroutines.*


class FilmListFragment : Fragment() {

    private lateinit var viewModel : FilmListViewModel
    private val filmAdapter = FilmAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_film_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FilmListViewModel::class.java)
        viewModel.refreshData()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = filmAdapter

        swipeRefreshLayout.setOnRefreshListener {
            progressBarLoading.visibility = View.VISIBLE
            tvFilmError.visibility = View.GONE
            recyclerView.visibility = View.GONE
            viewModel.refreshFromInternet()
            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()


    }

    fun observeLiveData(){

        viewModel.films.observe(viewLifecycleOwner, Observer { films ->
            films?.let {

                recyclerView.visibility = View.VISIBLE
                filmAdapter.updateFilms(films)

            }
        })

        viewModel.filmErrorMessage.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if(it){
                    tvFilmError.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    tvFilmError.visibility = View.GONE
                }
            }
        })

        viewModel.filmLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    recyclerView.visibility = View.GONE
                    tvFilmError.visibility = View.GONE
                    progressBarLoading.visibility = View.VISIBLE
                } else {
                    progressBarLoading.visibility = View.GONE
                }
            }
        })

    }


}