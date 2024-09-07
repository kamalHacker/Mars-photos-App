package com.example.marsphotosapp.data

import android.util.Log
import com.example.marsphotosapp.network.MarsApiService
import com.example.marsphotosapp.network.MarsPhoto
import retrofit2.Response

interface MarsPhotoRepository {
    suspend fun getMarsPhotos(): List<MarsPhoto>
}

class NetworkMarsPhotoRepository(
    private val marsApiService: MarsApiService
) : MarsPhotoRepository {
    override suspend fun getMarsPhotos():
        List<MarsPhoto> {
        val photos: Response<List<MarsPhoto>> = marsApiService.getPhotos()
        if (photos.isSuccessful) {
            val photosBody = photos.body()
            if (photosBody != null) {
                Log.d("NetworkMarsPhotoRepository", "Fetched photos: $photosBody")
                return photosBody
            } else {
                Log.d("NetworkMarsPhotoRepository", "Response body is null")
            }
        } else {
            Log.d("NetworkMarsPhotoRepository", "Response is not successful: ${photos.code()}")
        }
        return emptyList()
    }

}