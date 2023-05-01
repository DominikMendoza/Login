package com.zen.login.ui.login.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class SignUpViewModel : ViewModel(){
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _confirmPassword = MutableLiveData<String>()
    val confirmPassword: LiveData<String> = _confirmPassword

    private val _category = MutableLiveData<String>()
    val category: LiveData<String> = _category

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories

    private val _isContinueEnabled = MutableLiveData<Boolean>()
    val isContinueEnabled: LiveData<Boolean> = _isContinueEnabled

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val regex = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}\$")
        return regex.matches(password)
    }

    private fun isValidConfirmPassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    private fun isValidCategory(category: String): Boolean {
        return category.isNotEmpty()
    }

    fun onSignUpChanged(email: String, password: String, confirmPassword: String, category: String) {
        _email.value = email
        _password.value = password
        _confirmPassword.value = confirmPassword
        _category.value = category
        _isContinueEnabled.value = isValidEmail(email) && isValidPassword(password) &&
                isValidConfirmPassword(password, confirmPassword) && isValidCategory(category)
    }

    suspend fun onContinueClicked() {
        _isLoading.value = true
        delay(2000)
        _isLoading.value = false
    }
}