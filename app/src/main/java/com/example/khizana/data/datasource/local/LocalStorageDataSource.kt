package com.example.khizana.data.datasource.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocalStorageDataSource @Inject constructor(@ApplicationContext context: Context) {

    var prefs =
        context.getSharedPreferences(Strings.SHARED_PREF_NAME, Context.MODE_PRIVATE)
    var editor = prefs.edit()

    fun saveGetStartedStateState() {
        editor.putBoolean(Strings.SEEN_GET_STARTED, true)
        editor.apply()
    }

    val getStartedState: Boolean
        get() = prefs.getBoolean(Strings.SEEN_GET_STARTED, false)
}

object Strings{
    const val SHARED_PREF_NAME = "PREFS"
    const val SEEN_GET_STARTED = "SEEN_GET_STARTED"
}