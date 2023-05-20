package com.zen.login.ui.login.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zen.login.ui.login.data.model.Authentication
import com.zen.login.ui.login.data.remote.ApiClient
import com.zen.login.ui.login.repository.AuthenticationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginViewModel : ViewModel() {
    private val authenticationService = ApiClient.AuthenticationService()
    private val authenticationRepository = AuthenticationRepository(authenticationService)

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnabled = MutableLiveData<Boolean>()
    val loginEnabled: LiveData<Boolean> = _loginEnabled

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _authentication = authenticationRepository.authentication
    val authentication get() =  _authentication
    private val _token = authenticationRepository.token
    val token get() = _token

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        //_loginEnabled.value = isValidEmail(email) && isValidPassword(password)
        _loginEnabled.value = true
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }

    fun onLoginClicked() {
        authenticationRepository.signIn(Authentication(email.value!!, password.value!!))
        _token.value = authenticationRepository.token.value
    }
}