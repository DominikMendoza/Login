package com.zen.login.ui.login.ui

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


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
    val categories :List<String> by viewModel.categories.observeAsState(initial = listOf("Chofer", "Empresa"))
    val isContinueEnabled :Boolean by viewModel.isContinueEnabled.observeAsState(initial = false)
    val isLoading :Boolean by viewModel.isLoading.observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    if (isLoading) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Column(modifier = modifier) {
            EmailField(email = email, onTextFieldChanged = { viewModel.onSignUpChanged(it, password, confirmPassword, category) })
            Spacer(modifier = Modifier.padding(8.dp))
            PasswordField(password = password, onTextFieldChanged = { viewModel.onSignUpChanged(email, it, confirmPassword, category) })
            Spacer(modifier = Modifier.padding(8.dp))
            ConfirmPasswordField(confirmPassword = confirmPassword, onTextFieldChanged = { viewModel.onSignUpChanged(email, password, it, category) })
            Spacer(modifier = Modifier.padding(8.dp))
            CategoryField(categories = categories, selectedCategory = category, onTextFieldChanged = { viewModel.onSignUpChanged(email, password, confirmPassword, it) })
            Spacer(modifier = Modifier.padding(8.dp))
            ContinueButton(continueEnabled = isContinueEnabled) {
                coroutineScope.launch {
                    viewModel.onContinueClicked()
                }
            }
        }
    }
}

@Composable
fun ContinueButton(continueEnabled: Boolean, onContinueClicked: () -> Unit) {
    Button(
        onClick = { onContinueClicked() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF3F51B5),
            disabledContainerColor = Color(0xFF47535E),
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        enabled = continueEnabled

    ) {
        Text(text = "Continuar")
    }
}

@Composable
fun CategoryField(categories: List<String>, selectedCategory: String, onTextFieldChanged: (String) -> Unit) {
    Column{
        Text(
            text = "Category",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        categories.forEach { category ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 16.dp)
                    .selectable(
                        selected = category == selectedCategory,
                        onClick = { onTextFieldChanged(category) }
                    )
            ) {
                RadioButton(
                    selected = category == selectedCategory,
                    onClick = { onTextFieldChanged(category) },
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = category,
                    modifier = Modifier.padding(end = 16.dp),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
        
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmPasswordField(confirmPassword: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = confirmPassword,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .border(
                width = 4.dp,
                color = Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .background(Color.Blue, CutCornerShape(16.dp)),
        placeholder = {
            Text(
                text = "Confirm Password",
                color = Color.Black
            )
        },
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF000000),
            containerColor = Color(0xFF02AAEE),
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}
