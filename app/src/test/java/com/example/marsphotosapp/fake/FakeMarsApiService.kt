package com.example.marsphotosapp.fake

import com.example.marsphotosapp.network.MarsApiService
import com.example.marsphotosapp.network.MarsPhoto

class FakeMarsApiService : MarsApiService {
    override suspend fun getPhotos(): List<MarsPhoto> {
        return FakeDataSource.photosList
    }
}