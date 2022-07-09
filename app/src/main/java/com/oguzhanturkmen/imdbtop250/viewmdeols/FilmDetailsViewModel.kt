package com.oguzhanturkmen.imdbtop250.viewmdeols

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.oguzhanturkmen.imdbtop250.models.Item
import com.oguzhanturkmen.imdbtop250.service.FilmDatabase
import kotlinx.coroutines.launch

class FilmDetailsViewModel(application: Application): BaseViewModel(application){
    val FilmLiveData = MutableLiveData<Item>()

    fun getRoomData (uuid: Int){
        launch {
            val dao = FilmDatabase(getApplication()).FilmDao()
            val Film = dao.getFilm(uuid)
            FilmLiveData.value = Film
        }
    }

    fun SqliteUpdate(value:Boolean,uuid:Int){
        launch {
            val dao = FilmDatabase(getApplication()).FilmDao()
            dao.updateFilmFavorite(value,uuid)
            println("SqliteUpdate BookListViewModel : "+uuid)
        }
    }
}