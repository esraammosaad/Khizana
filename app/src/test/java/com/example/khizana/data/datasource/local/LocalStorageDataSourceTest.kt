package com.example.khizana.data.datasource.local

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalStorageDataSourceTest {

    private lateinit var dataSource: LocalStorageDataSource
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        sharedPrefs = mockk(relaxed = true)
        editor = mockk(relaxed = true)

        every { sharedPrefs.edit() } returns editor
        every { editor.putBoolean(any(), any()) } returns editor
        every { editor.apply() } returns Unit

        dataSource = LocalStorageDataSource(context).apply {
            this.prefs = sharedPrefs
            this.editor = editor
        }
    }

    @After
    fun tearDown() {
    }
    
    @Test
    fun getStartedStateReturnsFalseWhenNotSet() {
        // Given
        every { sharedPrefs.getBoolean(Strings.SEEN_GET_STARTED, false) } returns false

        // When
        val result = dataSource.getStartedState

        // Then
        assertThat(result , `is`(false))
    }

    @Test
    fun getStartedStateReturnsTrueWhenSet() {
        // Given
        every { sharedPrefs.getBoolean(Strings.SEEN_GET_STARTED, false) } returns true

        // When
        val result = dataSource.getStartedState

        // Then
        assertThat(result, `is`(true))
    }

}