package com.zen.login.ui.login.repository

import androidx.lifecycle.MutableLiveData
import com.zen.login.ui.login.data.model.Authentication
import com.zen.login.ui.login.data.remote.AuthenticationResponse
import com.zen.login.ui.login.data.remote.AuthenticationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthenticationRepository(
    private val authenticationService: AuthenticationService
) {
    private val _authentication = MutableLiveData<AuthenticationResponse>()
    val authentication get() = _authentication

    private val _token = MutableLiveData<String>("")
    val token get() = _token

    fun signIn(authentication: Authentication) {
        val signIn = authenticationService.signIn(authentication)

        signIn.enqueue(object : Callback<AuthenticationResponse> {
            override fun onResponse(
                call: Call<AuthenticationResponse>,
                response: Response<AuthenticationResponse>
            ) {
                if (response.isSuccessful) {
                    _authentication.value = response.body()!!
                    _token.value = response.body()!!.token
                }
            }

            override fun onFailure(call: retrofit2.Call<AuthenticationResponse>, t: Throwable) {
                _authentication.value = AuthenticationResponse(1, "Error", "Error", "Error", "Error")
            }
        })
    }
    //fun signIn(username: String, password: String) {
    //    signIn(Authentication(username, password))
    //}

    fun signOut() {
        _authentication.value = null
    }
}