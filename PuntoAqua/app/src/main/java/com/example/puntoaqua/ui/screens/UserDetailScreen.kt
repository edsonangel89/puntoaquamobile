package com.example.puntoaqua.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.puntoaqua.R
import com.example.puntoaqua.ui.theme.PuntoAquaTheme
import com.example.puntoaqua.data.UserExample

@Composable
fun UserDetailScreen(
    navigateToPoints: () -> Unit,
    valueText: String,
    onChangeValue: (String) -> Unit,
    logout: () -> Unit,
    getUserFun: () -> Unit,
    keyboardOptions: KeyboardOptions
) {
    val test = false
    Scaffold(
        topBar = {
            UserTopBar(
                topBarTitle = R.string.client_label,
                logoutFun = logout,
                navigate = navigateToPoints
            )
        }
    ) {PaddingValues ->
        Column {
            UserSearchForm(
                value = valueText,
                onChange = onChangeValue,
                keyboardOptions = keyboardOptions,
                getUserFun = getUserFun,
                modifier = Modifier
                    .padding(PaddingValues)
            )
            if(test) {
                UserInfo()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTopBar(
    @StringRes topBarTitle: Int,
    logoutFun: () -> Unit,
    navigate: () -> Unit
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
                    onClick = navigate
                ) {
                    Icon(imageVector = Icons.Filled.Add,
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
fun UserSearchForm(
    value: String,
    onChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    getUserFun: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 100.dp,
                start = 16.dp,
                end = 16.dp
            )
    ) {
        UserInput(
            textFieldValue = value,
            onChangeFunction = onChange,
            keyboardOptions = keyboardOptions
        )
        UserButtonItem(
            buttonText = R.string.client_search_button,
            getUserFun =

                /*Log.d("DEBUG", "UserButtonItem function")*/
                getUserFun
            ,
            modifier = Modifier
                .padding(
                    top = 16.dp
                )
                .fillMaxWidth()
        )
    }
}

@Composable
fun UserInput(
    textFieldValue: String,
    onChangeFunction: (String) -> Unit,
    keyboardOptions: KeyboardOptions
) {
    Column {
        Text(
            text = stringResource(R.string.clientid_label),
            fontSize = 30.sp,
            modifier = Modifier
                .padding(
                    bottom = 8.dp
                )
        )
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = onChangeFunction,
            keyboardOptions = keyboardOptions
        )
    }
}

@Composable
fun UserButtonItem(
    @StringRes buttonText: Int,
    getUserFun: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = getUserFun,
            /*Log.d("DEBUG", "Onclick button")*/
        modifier = modifier
    ) {
        Text(
            text = stringResource(buttonText),
            fontSize = 24.sp
        )
    }
}

@Composable
fun UserInfo() {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.id_label),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.3f)
            )
            Text(
                text = UserExample.id.toString(),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.fname_label),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.3f)
            )
            Text(text = UserExample.firstName,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.lname_label),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.3f)
            )
            Text(text = UserExample.lastName,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.points_user),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.3f)
                )
            Text(text = UserExample.points.toString(),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserDetailPreview() {
    PuntoAquaTheme {
        /*UserDetailScreen()*/
    }
}