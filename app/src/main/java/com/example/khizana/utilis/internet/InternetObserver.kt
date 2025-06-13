package com.example.khizana.utilis.internet

import kotlinx.coroutines.flow.Flow

interface InternetObserver {
    val isConnected : Flow<Boolean>
}