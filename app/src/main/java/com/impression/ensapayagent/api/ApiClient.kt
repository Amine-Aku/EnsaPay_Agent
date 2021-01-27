package com.impression.ensapayagent.api

import com.google.gson.GsonBuilder
import com.impression.ensapayagent.services.AgentService
import com.impression.ensapayagent.services.AuthenticationService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient {

    private const val baseUrl = "https://ensa-pay-agent.herokuapp.com/"
    private var retrofit: Retrofit? = null

    fun getRetrofitInstance(): Retrofit{
        if(retrofit == null){
            val gson = GsonBuilder().setLenient().create()

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .callTimeout(20, TimeUnit.SECONDS)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
        }
        return retrofit!!
    }

    fun getAuthenticationService(): AuthenticationService = getRetrofitInstance().create(AuthenticationService::class.java)

    fun getAppuserServices(): AgentService = getRetrofitInstance().create(AgentService::class.java)





}
