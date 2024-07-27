package com.example.puntoaqua.data

data class PuntoAquaUiState(
    val isUserLogged: Boolean = false,
    val userId: String = "",
    val userFname: String = "",
    val userLname: String = "",
    val email: String = "",
    val role: String = "",
    val token: String = ""
)
