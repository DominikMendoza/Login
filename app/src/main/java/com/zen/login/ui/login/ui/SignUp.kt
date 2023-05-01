package com.zen.login.ui.login.ui

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun SingUpScreen(viewModel: SignUpViewModel) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SignUp(Modifier.align(Alignment.Center), viewModel)
    }
}

@Composable
fun SignUp(modifier: Modifier, viewModel: SignUpViewModel) {
    val email :String by viewModel.email.observeAsState(initial = "")
    val password :String by viewModel.password.observeAsState(initial = "")
    val confirmPassword :String by viewModel.confirmPassword.observeAsState(initial = "")
    val category :String by viewModel.category.observeAsState(initial = "")
    val isContinueEnabled :Boolean by viewModel.isContinueEnabled.observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {
        EmailField(email = email, onTextFieldChanged = { viewModel.onSignUpChanged(it, password, confirmPassword, category) })
        Spacer(modifier = Modifier.padding(8.dp))
        PasswordField(password = password, onTextFieldChanged = { viewModel.onSignUpChanged(email, it, confirmPassword, category) })
        Spacer(modifier = Modifier.padding(8.dp))
        ConfirmPasswordField(confirmPassword = confirmPassword, onTextFieldChanged = { viewModel.onSignUpChanged(email, password, it, category) })
        Spacer(modifier = Modifier.padding(8.dp))
        CategoryField(category = category, onTextFieldChanged = { viewModel.onSignUpChanged(email, password, confirmPassword, it) })
    }
}

@Composable
fun CategoryField(category: String, onTextFieldChanged: (String) -> Unit) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmPasswordField(confirmPassword: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = confirmPassword,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = "Confirm Password",
                color = Color.Gray
            )
        },
        visualTransformation = PasswordVisualTransformation(),
    )
}
