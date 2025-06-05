package com.example.khizana.presentation.feature.login

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(navController: NavHostController? = null) {

    LazyColumn {


    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {

    LoginScreen()

}