package com.oguzhanturkmen.imdbtop250.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @ColumnInfo(name = "crew")
    val crew: String,
    @ColumnInfo(name = "fullTitle")
    val fullTitle: String,
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "imdbRating")
    val imDbRating: String,
    @ColumnInfo(name = "imdbRatingCount")
    val imDbRatingCount: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "rank")
    val rank: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "year")
    val year: String,
    @ColumnInfo(name = "book_favorite")
    var book_favorite:Boolean?=false

){
    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0

}