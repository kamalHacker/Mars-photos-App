package com.example.marsphotosapp.fake

import com.example.marsphotosapp.rules.TestDispatcherRule
import com.example.marsphotosapp.ui.screens.MarsUiState
import com.example.marsphotosapp.ui.screens.MarsViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class MarsViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun marsViewModel_getMarsPhotos_verifyMarsUiStateSuccess() =
        runTest{
            val marsViewModel = MarsViewModel(
                marsPhotoRepository = FakeNetworkMarsPhotoRepository()
            )
            assertEquals(
                MarsUiState.Loading,
                marsViewModel.marsUiState.value
            )
            
            marsViewModel.getMarsPhotos()
                advanceUntilIdle()

            val latestUiState = marsViewModel.marsUiState.value

            assertEquals(
                "Success(photos=Success: 2 Mars photos retrieved)",
                latestUiState.toString()
            )
        }
}