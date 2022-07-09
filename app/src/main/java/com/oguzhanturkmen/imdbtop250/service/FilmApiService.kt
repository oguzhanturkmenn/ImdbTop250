package com.oguzhanturkmen.imdbtop250.service

import com.oguzhanturkmen.imdbtop250.models.Model
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FilmApiService {

    private val BASE_URL = "https://imdb-api.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(FilmApi::class.java)

    fun getData() : Single<Model> {
        return api.getFilms()
    }

}