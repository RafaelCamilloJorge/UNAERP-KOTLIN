package com.unaerp.kotlinrestapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unaerp.kotlinrestapi.Model.RandomFact
import com.unaerp.kotlinrestapi.Services.IApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    private val _randomFact = MutableStateFlow<RandomFact?>(null)
    val randomFact: StateFlow<RandomFact?> = _randomFact.asStateFlow()

    private val apiService: IApiService

    init {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://uselessfacts.jsph.pl/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(IApiService::class.java)
    }

    fun fetchRandomFact() {
        viewModelScope.launch {
            try {
                val fact = withContext(Dispatchers.IO) {
                    apiService.getRandomFact()
                }
                _randomFact.update { fact }
            } catch (e: Exception) {
            }
        }
    }
}
