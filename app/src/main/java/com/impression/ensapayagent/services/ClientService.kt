package com.impression.ensapayagent.services

import com.impression.ensapayagent.model.Client
import com.impression.ensapayagent.model.ComptePayment
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ClientService {

    @GET("comptePay/getAllClients")
    fun getListClients(@Header("Authorization") token: String?): Call<List<ComptePayment>>

    @POST("comptePay/add")
    fun createClient(@Header("Authorization") token: String?, @Body newCompte: ComptePayment): Call<Void>

}
