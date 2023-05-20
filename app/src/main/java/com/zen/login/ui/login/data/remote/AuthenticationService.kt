package com.zen.login.ui.login.data.remote

import com.zen.login.ui.login.data.model.Authentication
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthenticationService {
    @POST("users/sign-in")
    fun signIn(@Body authentication: Authentication): Call<AuthenticationResponse>
}