package com.example.khizana.data.repo

import com.example.khizana.data.model.GetCollectsResponse
import com.example.khizana.data.remote.RemoteDataSourceImpl
import retrofit2.Response

class RepositoryImpl(private val remoteDataSourceImpl: RemoteDataSourceImpl) {

    suspend fun getCollects() : Response<GetCollectsResponse> {
        return remoteDataSourceImpl.getCollects()
    }

}