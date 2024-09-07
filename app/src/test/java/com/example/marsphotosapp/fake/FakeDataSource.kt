package com.example.marsphotosapp.fake

import com.example.marsphotosapp.network.MarsPhoto

object FakeDataSource {

    private const val idOne = "img1"
    private const val idTwo = "img2"
    private const val imgOne = "url.1"
    private const val imgTwo = "url.2"
    val photosList = listOf(
        MarsPhoto(
            id = idOne,
            imgSrc = imgOne
        ),
        MarsPhoto(
            id = idTwo,
            imgSrc = imgTwo
        )
    )
}