package com.oguzhanturkmen.imdbtop250.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.oguzhanturkmen.imdbtop250.R
import com.oguzhanturkmen.imdbtop250.databinding.FilmRowBinding
import com.oguzhanturkmen.imdbtop250.models.Item
import com.oguzhanturkmen.imdbtop250.models.Model
import com.oguzhanturkmen.imdbtop250.utils.downloadFromUrl
import com.oguzhanturkmen.imdbtop250.utils.placeHolderProgressBar
import com.oguzhanturkmen.imdbtop250.views.FilmListFragmentDirections
import kotlinx.android.synthetic.main.film_row.view.*

class FilmAdapter(val list : ArrayList<Item>)  : RecyclerView.Adapter<FilmAdapter.FilmHolder>(),ItemClickListener{

    class FilmHolder(var view:FilmRowBinding ) : RecyclerView.ViewHolder(view.root) {

    }

 override fun itemClicked(view: View) {
     val uuid = view.itemID.text.toString().toInt()

     val action = FilmListFragmentDirections.actionFilmListFragmentToFilmDetailsFragment()
     //action.actionId = uuid
     Navigation.findNavController(view).navigate(action)


    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<FilmRowBinding>(inflater,R.layout.film_row,parent,false)

        return FilmHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FilmHolder, position: Int) {
        holder.view.item = list[position]
        holder.view.listener = this
        /*
        holder.itemView.textViewFilmRowTitle.text = list[position].title
        holder.itemView.textViewFilmRowYear.text = list[position].year
        holder.itemView.textViewFilmRowImdbRank.text = list[position].imDbRating
        holder.itemView.setOnClickListener {
            val action = FilmListFragmentDirections.actionFilmListFragmentToFilmDetailsFragment(list.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.itemView.imageViewFilmRow.downloadFromUrl(list[position].image, placeHolderProgressBar(holder.itemView.context))
         */

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFilms(newFilmList: List<Item>){
        list.clear()
        list.addAll(newFilmList)
        notifyDataSetChanged()
    }



}