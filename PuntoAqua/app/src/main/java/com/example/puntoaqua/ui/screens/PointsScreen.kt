package com.example.puntoaqua.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puntoaqua.R
import com.example.puntoaqua.ui.PuntoAquaUiState
import com.example.puntoaqua.ui.ViewModelProvider
import com.example.puntoaqua.ui.theme.PuntoAquaTheme
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

@Composable
fun PointsScreen(
    textValue: String,
    valueChange: (String) -> Unit,
    navigateToUserDetails: () -> Unit,
    logout: () -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {

    Scaffold(
        topBar = {
            PointsTapBar(
                topBarTitle = R.string.points_label,
                logoutFun = logout,
                onAccountBoxClick = navigateToUserDetails
            )
        },
        modifier = Modifier
            .fillMaxSize()
    )
    {paddingValues ->
          PointsInput(
              labelInput = R.string.points_label,
              valueTextField = textValue,
              onValueChange = valueChange,
              keyboardOptions = keyboardOptions,
              modifier = Modifier
                  .padding(paddingValues)
          )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PointsTapBar(
    @StringRes topBarTitle: Int,
    logoutFun: () -> Unit,
    onAccountBoxClick: () -> Unit
) {
     TopAppBar(
         title = { Text(text = stringResource(topBarTitle)) },
         modifier = Modifier
             .background(Color.Cyan),
         colors = TopAppBarDefaults.mediumTopAppBarColors(
              containerColor = Color.LightGray
         ),
         actions = {
             Row {
                 IconButton(
                     onClick = onAccountBoxClick
                 ) {
                     Icon(imageVector = Icons.Filled.AccountBox,
                         contentDescription = null
                     )
                 }
                 IconButton(
                     onClick = logoutFun
                 ) {
                     Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = null)
                 }
             }
         }
     )
}

@Composable
fun PointsInput(
    @StringRes labelInput: Int,
    valueTextField: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    Column (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 100.dp
            )
    ){
        Text(
            text = stringResource(labelInput),
            fontSize = 36.sp,
            modifier = Modifier
                .padding(
                    bottom = 8.dp
                )
        )
        OutlinedTextField(
            value = valueTextField,
            onValueChange = onValueChange,
            keyboardOptions = keyboardOptions
        )
        ButtonItem()
    }
}

@Composable
fun ButtonItem() {
    Button(
        modifier = Modifier
            .padding(
                top = 16.dp
            )
            .fillMaxWidth(0.9f),
        onClick = { /*TODO*/ }
    ) {
         Text(
             text = "Agregar",
             fontSize = 24.sp
         )
    }
}

@Preview(showBackground = true)
@Composable
fun PointsPreview() {
    PuntoAquaTheme {
        /*PointsScreen()*/
    }
}