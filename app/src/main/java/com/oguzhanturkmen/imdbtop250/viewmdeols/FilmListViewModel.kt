package com.oguzhanturkmen.imdbtop250.viewmdeols

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.oguzhanturkmen.imdbtop250.models.Item
import com.oguzhanturkmen.imdbtop250.models.Model
import com.oguzhanturkmen.imdbtop250.service.FilmApiService
import com.oguzhanturkmen.imdbtop250.service.FilmDatabase
import com.oguzhanturkmen.imdbtop250.utils.PrivateSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FilmListViewModel(application: Application) : BaseViewModel(application) {
    val films = MutableLiveData<List<Item>>()
    val filmErrorMessage = MutableLiveData<Boolean>()
    val filmLoading = MutableLiveData<Boolean>()

    private val filmApiService = FilmApiService()
    private val disposable = CompositeDisposable()
    private val privateSharedPreferences = PrivateSharedPreferences(getApplication())
    private var updateTime = 0.1f * 60 * 60 * 1000 * 1000 * 1000L

    fun refreshData(){
        val saveTime = privateSharedPreferences.getTime()

        if (saveTime != null && saveTime != 0L && System.nanoTime() - saveTime < updateTime){
            //10 dk nın altında zaman olduğu için SQLite'dan veriyi al
            getDataFromSQLite()
        }else{
            getDataFromInternet()
        }
    }

    fun refreshFromInternet(){
        getDataFromInternet()
    }
    private fun getDataFromSQLite(){
        filmLoading.value = true
        launch {
            val FilmList = FilmDatabase(getApplication()).FilmDao().getAllFilms()
            showFilms(FilmList)
            Toast.makeText(getApplication(),"get Room", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getDataFromInternet() {
        filmLoading.value = true
        disposable.add(
            filmApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Model>() {
                    override fun onSuccess(t: Model) {
                        //Başarılı olursa
                        saveFilmsInSQLite(t.items)
                        Toast.makeText(getApplication(),"Besinleri Internet'ten Aldık", Toast.LENGTH_LONG).show()

                    }

                    override fun onError(e: Throwable) {
                        //Hata alırsak
                        filmErrorMessage.value = true
                        filmLoading.value = false
                        e.printStackTrace()
                    }

                })

        )

    }

    private fun showFilms(filmsList : List<Item>){
        films.value = filmsList
        filmLoading.value = false
        filmErrorMessage.value = false
    }

    private fun saveFilmsInSQLite(filmsList : List<Item>){
        launch {
            val dao = FilmDatabase(getApplication()).FilmDao()
            dao.deleteAllFilms()
            val uuidList = dao.insertAll(*filmsList.toTypedArray())
            var i = 0
            //sqliteta oluşturulan veri uuidlerini modelimin içine koyuyorum bu işlemle
            //amaç ise modelimin içinde de bu idlere erişebilim böylece bu idleri kullanabilim istediğim zaman
            while (i < filmsList.size){
                filmsList[i].uuid = uuidList [i].toInt()
                i += 1
            }
            showFilms(filmsList)

        }
        privateSharedPreferences.saveTime(System.nanoTime())

    }

}