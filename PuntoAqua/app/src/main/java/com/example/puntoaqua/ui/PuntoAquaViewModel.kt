package com.example.puntoaqua.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.puntoaqua.PuntoAquaApplication
import com.example.puntoaqua.data.PuntoAquaUiState
import com.example.puntoaqua.repositories.LocalRepository
import com.example.puntoaqua.repositories.UserStateRepository
import com.example.puntoaqua.ui.screens.LoginViewModel
import com.example.puntoaqua.ui.screens.PointsViewModel
import com.example.puntoaqua.ui.screens.UserDetailViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class PuntoAquaViewModel(
    private val userPreferencesRepository: LocalRepository
) : ViewModel() {

    val uiState: StateFlow<PuntoAquaUiState> = UserStateRepository.uiState

    val userState =
        userPreferencesRepository.userState.map { isUserLogged ->
                 UserStateRepository.setUserLoggedState(isUserLogged)
            }

    val userToken =
        userPreferencesRepository.token.map { token ->
            UserStateRepository._uiState.update { cState ->
                cState.copy(
                    token = token
                )
            }
        }

    /*FIND THE WAY TO IMPLEMENT APPLICATION ONCE AND USE IT IN ALL VIEW MODELS*/

    companion object {

        val factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = (this[APPLICATION_KEY] as PuntoAquaApplication)
                    val userPreferencesRepository = application.localRepository
                    PuntoAquaViewModel(userPreferencesRepository)
                }
                initializer {
                    val application = (this[APPLICATION_KEY] as PuntoAquaApplication)
                    val userDbRepository = application.container.userDbRepository
                    val userPreferencesRepository = application.localRepository
                    /*val netState = application.netState*/
                    LoginViewModel(userDbRepository, userPreferencesRepository)
                }
                initializer {
                    val application = (this[APPLICATION_KEY] as PuntoAquaApplication)
                    val userDbRepository = application.container.userDbRepository
                    /*val netState = application.netState*/
                    UserDetailViewModel(userDbRepository)
                }
                initializer {
                    val application = (this[APPLICATION_KEY] as PuntoAquaApplication)
                    val userDbRepository = application.container.userDbRepository
                    /*val netState = application.netState*/
                    PointsViewModel(userDbRepository)
                }
            }
    }
}