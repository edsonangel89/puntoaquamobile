package com.example.puntoaqua.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.puntoaqua.ui.screens.Login
import com.example.puntoaqua.ui.screens.LoginViewModel
import com.example.puntoaqua.ui.screens.PointsScreen
import com.example.puntoaqua.ui.screens.PointsViewModel
import com.example.puntoaqua.ui.screens.UserDetailScreen
import com.example.puntoaqua.ui.screens.UserDetailViewModel
import io.ktor.websocket.Frame

@Composable
fun PuntoAquaApp(
    appViewModelProvider: PuntoAquaViewModel = viewModel(factory = PuntoAquaViewModel.factory),
    userViewModel: LoginViewModel = viewModel(factory = LoginViewModel.factory),
    pointsViewModel: PointsViewModel = viewModel(),
    userDetailViewModel: UserDetailViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val userState = appViewModelProvider.userState.collectAsState(PuntoAquaUiState()).value
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "home") {
            if(userState.isUserLogged) {
                PointsScreen(
                    textValue = pointsViewModel.pointsCounter,
                    valueChange =  { pointsViewModel.updatePointsCounter(it) },
                    logout = { userViewModel.logout(userState.userId) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                       keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    navigateToUserDetails = { navController.navigate("userdetail") }
                )
            }
            else {
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
                navigateToPoints = { navController.navigate("home") }
            )
        }
    }
}