package com.impression.ensapayagent.services

import com.impression.ensapayagent.model.Agent
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface AgentService {

    @GET("user/{id}")
    fun getUser(@Header("Authorization") token: String?): Call<Agent>

    @POST("register")
    fun registerUser(@Body newUser: Agent): Call<String>

}
