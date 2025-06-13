package com.example.khizana.data.datasource.local

import android.content.Context
import com.example.khizana.utilis.Strings
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocalStorageDataSource @Inject constructor(@ApplicationContext context: Context) {

    private var prefs =
        context.getSharedPreferences(Strings.SHARED_PREF_NAME, Context.MODE_PRIVATE)
    private var editor = prefs.edit()

    fun saveGetStartedStateState() {
        editor.putBoolean(Strings.SEEN_GET_STARTED, true)
        editor.apply()
    }

    val getStartedState: Boolean
        get() = prefs.getBoolean(Strings.SEEN_GET_STARTED, false)
}