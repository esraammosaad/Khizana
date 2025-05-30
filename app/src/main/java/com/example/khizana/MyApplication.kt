package com.example.khizana

import android.app.Application
import com.cloudinary.android.MediaManager

class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()


        val config = HashMap<String, String>()
        config["cloud_name"] = "deeqqeune"
        config["api_key"] = "464665185487627"
        config["api_secret"] = "aJi8ae2N2V_cBXE4vl5c0LBV7L8"

        MediaManager.init(this, config)


    }


}