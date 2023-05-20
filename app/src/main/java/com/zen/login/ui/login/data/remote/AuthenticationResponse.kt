package com.zen.login.ui.login.data.remote

import com.google.gson.annotations.SerializedName

data class AuthenticationResponse(
    val id: Int,
    val firstName : String,
    val lastName : String,
    @SerializedName("username")
    val userName : String,
    val token: String

)