package com.oguzhanturkmen.imdbtop250.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PrivateSharedPreferences {

    companion object {

        private val timeString = "time"
        private var sharedPreferences : SharedPreferences? = null

        @Volatile private var instance : PrivateSharedPreferences? = null

        private val lock = Any()
        operator fun invoke(context: Context) : PrivateSharedPreferences = instance ?: synchronized(lock) {
            instance ?: createPrivateSharedPreferences(context).also {
                instance = it
            }
        }

        private fun createPrivateSharedPreferences(context: Context):PrivateSharedPreferences {
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return PrivateSharedPreferences()
        }
    }

    fun saveTime(timeData:Long){
        sharedPreferences?.edit(commit = true){
            putLong(timeString,timeData)
        }
    }

    fun getTime() = sharedPreferences?.getLong(timeString,0)

}