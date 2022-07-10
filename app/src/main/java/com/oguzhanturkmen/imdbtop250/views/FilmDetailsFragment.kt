package com.oguzhanturkmen.imdbtop250.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.oguzhanturkmen.imdbtop250.R
import com.oguzhanturkmen.imdbtop250.databinding.FragmentFilmDetailsBinding
import com.oguzhanturkmen.imdbtop250.utils.downloadFromUrl
import com.oguzhanturkmen.imdbtop250.utils.placeHolderProgressBar
import com.oguzhanturkmen.imdbtop250.viewmdeols.FilmDetailsViewModel
import kotlinx.android.synthetic.main.film_row.*
import kotlinx.android.synthetic.main.fragment_film_details.*


class FilmDetailsFragment : Fragment() {
    private lateinit var viewModel : FilmDetailsViewModel
    private var filmId = 0
    private lateinit var dataBinding : FragmentFilmDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_film_details,container,false)
        return dataBinding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            filmId = FilmDetailsFragmentArgs.fromBundle(it).filmID
        }
        viewModel = ViewModelProviders.of(this).get(FilmDetailsViewModel::class.java)
        viewModel.getRoomData(filmId)
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.FilmLiveData.observe(viewLifecycleOwner, Observer { Film ->
            Film?.let {Film ->
                dataBinding.item = Film
                movieRatingBar.rating = Film.imDbRating.toFloat()
                movieRatingBar.isEnabled = false
                (activity as AppCompatActivity).supportActionBar?.title = Film.title
                    textViewFilmDetailFavorite.setOnClickListener {
                    favoriteImage(Film.book_favorite != true, Film.uuid)
                    if (Film.book_favorite == true){
                        favoriteImage(false,Film.uuid)
                    }else{
                        favoriteImage(true,Film.uuid)
                    }
                }


            }
        })
    }

    fun favoriteImage(value:Boolean,id:Int){
        viewModel.SqliteUpdate(value,id)
        viewModel.getRoomData(id)
    }
}