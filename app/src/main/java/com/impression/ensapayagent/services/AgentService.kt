package com.impression.ensapayagent.services

import com.impression.ensapayagent.model.Agent
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface AgentService {

    @GET("userApp/currentAgent")
    fun getUser(@Header("Authorization") token: String?): Call<Agent>

    @GET("userApp/isFirstAuth")
    fun isFirstAuth(@Header("Authorization") token: String?): Call<Boolean>

    @POST("userApp/changeMdp")
    fun changeMdp(@Header("Authorization") token: String?, @Body newMdp: String): Call<Void>

}
