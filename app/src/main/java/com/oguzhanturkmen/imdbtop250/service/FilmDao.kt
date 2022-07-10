package com.oguzhanturkmen.imdbtop250.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.oguzhanturkmen.imdbtop250.models.Item

@Dao
interface FilmDao {

    //Data Access Object

    @Insert
    suspend fun insertAll(vararg item: Item): List<Long>

    //Insert -> Room, insert into
    // suspend -> coroutine scope
    // vararg -> birden fazla ve istediğimiz sayıda besin
    // List<Long> -> long, id'ler

    @Query("SELECT * FROM Item")
    suspend fun getAllFilms() : List<Item>

    @Query("SELECT * FROM Item WHERE uuid = :filmId")
    suspend fun getFilm(filmId : Int) : Item

    @Query("DELETE FROM Item")
    suspend fun deleteAllFilms()

    @Query("UPDATE Item SET book_favorite=:value WHERE uuid = :bookID")
    suspend fun updateFilmFavorite(value:Boolean, bookID: Int)

}