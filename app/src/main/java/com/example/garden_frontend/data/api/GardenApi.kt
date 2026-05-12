package com.example.garden_frontend.data.api

import com.example.garden_frontend.data.api.dto.BerryBushCreation
import com.example.garden_frontend.data.api.dto.CareResourceDto
import com.example.garden_frontend.data.api.dto.FertilizerCreation
import com.example.garden_frontend.data.api.dto.FruitTreeCreation
import com.example.garden_frontend.data.api.dto.GardenStatisticsDto
import com.example.garden_frontend.data.api.dto.HarvestCreateRequest
import com.example.garden_frontend.data.api.dto.PestControlCreation
import com.example.garden_frontend.data.api.dto.PlantModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.garden_frontend.data.api.dto.UserReturnResponse
import com.example.garden_frontend.data.api.dto.UserLoginRequest
import com.example.garden_frontend.data.api.dto.UserRegRequest
import com.example.garden_frontend.domain.models.BerryBush
import com.example.garden_frontend.domain.models.CareResource
import com.example.garden_frontend.domain.models.FruitTree
import com.example.garden_frontend.domain.models.Harvest
import com.example.garden_frontend.domain.models.PestControl
import com.example.garden_frontend.domain.models.Plant
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface GardenApi {
    @POST("api/Auth/login")
    suspend fun login(@Body request: UserLoginRequest): Response<UserReturnResponse>

    @POST("api/Auth/register")
    suspend fun register(@Body request: UserRegRequest): Response<UserReturnResponse>

    @GET("api/Auth/me")
    suspend fun me(@Header("Authorization") token: String): Response<UserReturnResponse>

    @GET("api/Plants")
    suspend fun allPlants(@Header("Authorization") token: String): Response<List<PlantModel>>

    @POST("api/BerryBush")
    suspend fun postBerryBush(@Header("Authorization") token: String, @Body berryBush: BerryBushCreation): Response<BerryBush>

    @POST("api/FruitTree")
    suspend fun postFruitTree(@Header("Authorization") token: String, @Body fruitTree: FruitTreeCreation): Response<FruitTree>

    @GET("api/Harvests")
    suspend fun getAllHarvests(@Header("Authorization") token: String) : Response<List<Harvest>>

    @GET("api/CareResources")
    suspend fun getAllCareResources(@Header("Authorization") token: String) : Response<List<CareResourceDto>>

    @GET("api/FruitTree/{id}")
    suspend fun getFruitTreeById(@Path("id") id: Int, @Header("Authorization") token: String) : Response<FruitTree>

    @GET("api/BerryBush/{id}")
    suspend fun getBerryBush(@Path("id") id: Int, @Header("Authorization") token: String) : Response<BerryBush>

    @POST("api/Harvests")
    suspend fun postHarvest(@Header("Authorization") token: String, @Body harvest: HarvestCreateRequest) : Response<Harvest>

    @GET("api/Harvests/{plantId}")
    suspend fun getHarvestsByPlant(@Path("plantId") plantId: Int, @Header("Authorization") token: String) : Response<Harvest>

    @PATCH("api/CareResources/{id}/quantity")
    suspend fun updCareResourceQuantity(@Path("id") id: Int, @Header("Authorization") token: String, @Body quantity: Int): Response<CareResourceDto>

    @POST("api/CareResources/pest-control")
    suspend fun postPestControl(@Header("Authorization") token: String, @Body pestControl: PestControlCreation): Response<CareResourceDto>

    @POST("api/CareResources/fertilizer")
    suspend fun postFertilizer(@Header("Authorization") token: String, @Body fertilizer: FertilizerCreation): Response<CareResourceDto>

    @DELETE("api/CareResources/{id}")
    suspend fun deleteCareResource(@Path("id") id: Int, @Header("Authorization") token: String): Response<Unit>

    @GET("api/Statistics")
    suspend fun getStats(@Header("Authorization") token: String): Response<GardenStatisticsDto>
}