package com.example.pokedex.network

import androidx.lifecycle.MutableLiveData
import com.example.pokedex.model.PokemonResult
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import retrofit2.Response
import java.lang.reflect.Type
import java.net.UnknownHostException

object RetrofitRequest : KoinComponent{
    val type: Type = object : TypeToken<ErrorResponse>() {}.type

    suspend fun <T> doRetrofitRequest(
        requestMethod: String,
        call: suspend () -> Response<T>?
    ): RetrofitTreatedRequest<T> {
        return try {
            val response = withContext(Dispatchers.IO) { call.invoke() }
            when (response?.code()) {
                in 200..299 -> {
                    RetrofitTreatedRequest(response = response?.body(), isSuccess = true)
                }
                400 -> {
                    RetrofitTreatedRequest(
                        hasError = true,
                        message = ERROR_400
                    )
                }
                else -> {
                    RetrofitTreatedRequest(
                        hasError = true,
                        message = UNEXPECTED_ERROR
                    )
                }
            }
        } catch (e: UnknownHostException) {
            RetrofitTreatedRequest(hasError = true, message = UNEXPECTED_ERROR)
        }
    }

    data class RetrofitTreatedRequest<T>(
        val response: T? = null,
        val hasError: Boolean = false,
        val isSuccess: Boolean = false,
        val message: String? = null
    )

    data class ErrorResponse(
        val status: Int,
        val message: String
    )

    val ERROR_400 = "Erro 400"
    val UNEXPECTED_ERROR = "Erro inesperado"
}