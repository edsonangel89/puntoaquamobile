package com.example.puntoaqua.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.puntoaqua.R
import com.example.puntoaqua.ui.theme.PuntoAquaTheme

/*
@Composable
fun LoginScreen(
    username: String,

    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        LoginInputs(

        )
    }
}

LoginScreen(
        username = viewModel.username,
        onUserNameChange = { viewModel.updateUserName(it) },
        password = viewModel.userpassword,
        onPasswordChange = { viewModel.updatePassword(it) },
        onLoginSubmit = { viewModel.submitLogin() },
        modifier = Modifier
            .fillMaxSize()
    )
 */
//TEST
@Composable
fun Login(
    username: String,
    onUserNameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginSubmit: () -> Unit,
    modifier: Modifier
) {
    LoginScreen(
        username = username,
        onUserNameChange = onUserNameChange ,
        password = password,
        onPasswordChange = onPasswordChange,
        onLoginSubmit = onLoginSubmit,
        modifier = Modifier
            .fillMaxSize()
    )
}

@Composable
fun LoginScreen(
    username: String,
    onUserNameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginSubmit: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .background(Color.White)
    ) {
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
        ){
            UserInput(
                inputLabel = R.string.username_input_login,
                inputInput = username,
                valueChangeFun = onUserNameChange,
                textPlaceholder = { Text(text = "Ingresa tu correo electronico") }
            )
            UserInput(
                inputLabel = R.string.userpassword_input_login,
                inputInput = password,
                valueChangeFun = onPasswordChange,
                textPlaceholder = { Text(text = "Ingresa tu contrasena") }
            )
            ButtonLogin(
                onSubmit = onLoginSubmit
            )
        }
    }
}

@Composable
fun UserInput(
    @StringRes inputLabel: Int,
    inputInput: String,
    valueChangeFun: (String) -> Unit,
    textPlaceholder: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = stringResource(inputLabel),
            fontSize = 30.sp,
            modifier = Modifier
                .padding(
                    top = 16.dp
                )
        )
        if(stringResource(inputLabel) == "Contraseña") {
            OutlinedTextField(
                value = inputInput,
                onValueChange = valueChangeFun,
                label = textPlaceholder,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        else if(stringResource(inputLabel) == "Usuario") {
            OutlinedTextField(
                value = inputInput,
                onValueChange = valueChangeFun,
                label = textPlaceholder,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        else {
            OutlinedTextField(
                value = inputInput,
                onValueChange = valueChangeFun,
                label = textPlaceholder,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun ButtonLogin(
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onSubmit,
        modifier = Modifier
            .padding(
                top = 24.dp
            )
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.button_input_login_text),
            fontSize = 30.sp,
        )
    }
}

@Preview(showBackground = false)
@Composable
fun PuntoAquaPreview() {
    PuntoAquaTheme {

    }
}