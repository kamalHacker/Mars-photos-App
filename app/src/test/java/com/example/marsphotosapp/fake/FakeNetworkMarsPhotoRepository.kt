package com.example.marsphotosapp.fake

import com.example.marsphotosapp.data.MarsPhotoRepository
import com.example.marsphotosapp.network.MarsPhoto
import kotlinx.coroutines.delay

class FakeNetworkMarsPhotoRepository : MarsPhotoRepository {
    override suspend fun getMarsPhotos(): List<MarsPhoto> {
        delay(100)
        return listOf(
            MarsPhoto("1", "url.1"),
            MarsPhoto("2", "url.2")
        )
    }

}