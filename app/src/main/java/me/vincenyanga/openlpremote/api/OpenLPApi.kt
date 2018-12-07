package me.vincenyanga.openlpremote.api

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import me.vincenyanga.openlpremote.model.LiveDisplayData
import me.vincenyanga.openlpremote.model.RequestResult
import me.vincenyanga.openlpremote.model.ServiceData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenLPApi {

    @GET("api/service/list")
    fun getServiceData(): Call<BaseResponse<ServiceData>>

    @GET("api/service/set")
    fun sendItemToLiveView(@Query("data") request: String): Call<BaseResponse<RequestResult>>

    @GET("api/controller/live/text")
    fun getLiveDisplayData(): Call<BaseResponse<LiveDisplayData>>

    @GET("api/controller/live/set")
    fun selectSlide(@Query("data") request: String): Call<BaseResponse<RequestResult>>

    @GET("api/controller/live/next")
    fun goToNextSlide(): Call<BaseResponse<RequestResult>>

    @GET("api/controller/live/previous")
    fun goToPreviousSlide(): Call<BaseResponse<RequestResult>>

    @GET("api/alert")
    fun displayAlert(@Query("data") alert: String): Call<BaseResponse<RequestResult>>

    @GET("api/plugin/search")
    fun getSearchablePlugins(): Call<BaseResponse<JsonObject>>

    @GET("api/{plugin}/search")
    fun search(@Path("plugin")plugin: String, @Query("data") searchData: String): Call<BaseResponse<JsonObject>>

    @GET("api/{plugin}/add")
    fun addToService(@Path("plugin") plugin: String, @Query("data") data: String): Call<Unit>

    @GET("api/{plugin}/live")
    fun addToLive(@Path("plugin") plugin: String, @Query("data") data: String): Call<Unit>

}