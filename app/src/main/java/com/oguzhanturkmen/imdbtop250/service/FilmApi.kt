package com.oguzhanturkmen.imdbtop250.service


import com.oguzhanturkmen.imdbtop250.models.Model
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface FilmApi {
    //https://imdb-api.com/en/API/Top250Movies/k_az813mud
    @GET("en/API/Top250Movies/k_az813mud")

    fun getFilms () : Single<Model>
}