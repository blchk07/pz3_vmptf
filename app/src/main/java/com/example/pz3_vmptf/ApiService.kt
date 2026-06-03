package com.example.pz3_vmptf

import retrofit2.http.GET

interface ApiService {

    @GET("statuses.json")
    suspend fun getAlarmStatuses(): AlarmResponse
}