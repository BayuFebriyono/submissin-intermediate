package site.encryptdev.submissionawalintermediate.data.remote.retrofit

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import site.encryptdev.submissionawalintermediate.data.remote.response.FileUploadResponse
import site.encryptdev.submissionawalintermediate.data.remote.response.LoginResponse
import site.encryptdev.submissionawalintermediate.data.remote.response.RegisterResponse
import site.encryptdev.submissionawalintermediate.data.remote.response.StoryResponse

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("stories")
    suspend fun getStories(@Header("Authorization") token: String,
                           @Query("page") page: Int = 1,
                           @Query("size") size: Int = 20
    ): StoryResponse

    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Query("location") location : Int = 1,
        @Header("Authorization") token: String
    ): StoryResponse

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): FileUploadResponse
}