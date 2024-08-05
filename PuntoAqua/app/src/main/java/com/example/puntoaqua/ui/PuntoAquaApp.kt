package com.example.puntoaqua.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.puntoaqua.data.ConnectivityState
import com.example.puntoaqua.ui.screens.Login
import com.example.puntoaqua.ui.screens.LoginViewModel
import com.example.puntoaqua.ui.screens.PointsScreen
import com.example.puntoaqua.ui.screens.PointsViewModel
import com.example.puntoaqua.ui.screens.UserDetailScreen
import com.example.puntoaqua.ui.screens.UserDetailViewModel

@Composable
fun PuntoAquaApp(
    appViewModelProvider: PuntoAquaViewModel = viewModel(factory = PuntoAquaViewModel.factory),
    userViewModel: LoginViewModel = viewModel(factory = PuntoAquaViewModel.factory),
    pointsViewModel: PointsViewModel = viewModel(factory = PuntoAquaViewModel.factory),
    userDetailViewModel: UserDetailViewModel = viewModel(factory = PuntoAquaViewModel.factory),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    appViewModelProvider.userState.collectAsState("").value
    appViewModelProvider.userToken.collectAsState("").value
    val userState = appViewModelProvider.uiState.collectAsState().value
    val userInfo = userDetailViewModel.userDetailsState.collectAsState().value
    val loginState = remember { userViewModel.errorLogin }
    val promoState = remember { pointsViewModel.promoState }
    val userExist = remember { userDetailViewModel.userExist }
    val errorState = remember { pointsViewModel.errorState }
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "home") {
            if(userState.isUserLogged) {
                when {
                    promoState.value -> {
                        AlertDialog(
                            text = { Text(text = "PROMOCION") },
                            onDismissRequest = { },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        promoState.value = false
                                    }
                                ) {
                                    Text(text = "Confirmar")
                                }
                            }
                        )
                    }
                }
                when {
                    errorState.value -> {
                        AlertDialog(
                            text = { Text(text = "Error") },
                            onDismissRequest = { },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        errorState.value = false
                                    }
                                ) {
                                    Text(text = "OK")
                                }
                            }
                        )
                    }
                }
                PointsScreen(
                    idValue = pointsViewModel.uid,
                    idChange = { pointsViewModel.updateIdTextField(it) },
                    textValue = pointsViewModel.pointsCounter,
                    valueChange =  { pointsViewModel.updatePointsCounter(it) },
                    logout = { userViewModel.logout(userState.userId) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    navigateToUserDetails = { navController.navigate("userdetail") },
                    updatePoints =  { pointsViewModel.updatePoints(pointsViewModel.uid, pointsViewModel.pointsCounter) }
                )
                Toast.makeText(context, "TEST",Toast.LENGTH_SHORT).show()
            }
            else {
                when {
                    loginState.value -> {
                        AlertDialog(
                            text = { Text(text = "Usuario y/o contrasena incorrectos") },
                            onDismissRequest = { },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        loginState.value = false
                                    }
                                ) {
                                    Text(text = "Confirmar")
                                }
                            }
                        )
                    }
                }
                Login(
                    username = userViewModel.username,
                    onUserNameChange = { userViewModel.updateUserName(it) },
                    password = userViewModel.userpassword,
                    onPasswordChange = { userViewModel.updatePassword(it) },
                    onLoginSubmit = { userViewModel.submitLogin() },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
        composable(route = "userdetail") {
            when {
                userExist.value -> {
                    AlertDialog(
                        text = { Text(text = "Usuario no encontrado") },
                        onDismissRequest = { },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    userExist.value = false
                                }
                            ) {
                                Text(text = "Confirmar")
                            }
                        }
                    )
                }
            }
            UserDetailScreen(
                valueText = userDetailViewModel.userId,
                onChangeValue = { userDetailViewModel.updateId(it) },
                logout = {
                    userViewModel.logout(userState.userId)
                    navController.navigate("home")
                 },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                getUserFun = { userDetailViewModel.getUser(userDetailViewModel.userId) },
                navigateToPoints = { navController.navigate("home") },
                user = userInfo
            )
        }
    }
}