package com.example.marsphotosapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.marsphotosapp.MarsPhotoApplication
import com.example.marsphotosapp.data.MarsPhotoRepository
import com.example.marsphotosapp.network.MarsPhoto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface MarsUiState {
    data class Success(val photos: List<MarsPhoto>) : MarsUiState
    data object Error : MarsUiState
    data object Loading : MarsUiState
}

class MarsViewModel(
    private val marsPhotoRepository: MarsPhotoRepository
) : ViewModel() {
    private val _marsUiState = MutableStateFlow<MarsUiState>(MarsUiState.Loading)
    val marsUiState = _marsUiState.asStateFlow()

    init {
        getMarsPhotos()
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MarsPhotoApplication)
                val marsPhotoRepository = application.container.marsPhotoRepository
                MarsViewModel(marsPhotoRepository = marsPhotoRepository)
            }
        }
    }

    fun getMarsPhotos() {
        viewModelScope.launch {
            _marsUiState.value = try {
                MarsUiState.Success(marsPhotoRepository.getMarsPhotos())
            }catch (e: IOException) {
                MarsUiState.Error
            }
            catch (e: HttpException) {
                MarsUiState.Error
            }
        }
    }
}