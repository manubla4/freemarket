package com.manubla.freemarket.data.source.network.service

import com.manubla.freemarket.data.model.business.State
import retrofit2.http.GET
import retrofit2.http.Path

interface StateService {

    @GET("classified_locations/states/{stateId}")
    suspend fun fetchState(@Path("stateId") stateId: String): State

}
