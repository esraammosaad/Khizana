package com.example.khizana.presentation.feature.login.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.khizana.R
import com.example.khizana.presentation.feature.landing.CustomButton
import com.example.khizana.presentation.feature.login.viewModel.LoginViewModel
import com.example.khizana.presentation.feature.products.view.components.CustomTextField
import com.example.khizana.ui.theme.offWhiteColor
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.utilis.NavigationRoutes
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(navController: NavHostController, loginViewModel: LoginViewModel) {

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }
    val emailError = remember { mutableStateOf(false) }
    val emailErrorMessage = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf(false) }
    val passwordErrorMessage = remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        println(it)
        LazyColumn(
            Modifier
                .padding(top = 64.dp)
                .background(color = offWhiteColor)
                .fillMaxSize(),
        ) {
            item {

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(R.drawable.bg),
                            contentDescription = "",
                            modifier = Modifier.size(40.dp),
                        )
                        Text(
                            "Khizana.", style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                            ),
                            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Hi Welcome Back Admin!",
                        style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 6.dp)
                    )
                }
                Spacer(modifier = Modifier.height(180.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight / 2)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
                            ),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 80.dp, start = 16.dp, end = 16.dp),
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.Top
                        ) {
                            CustomTextField(
                                value = email,
                                label = "Email",
                                error = emailError.value,
                                errorMessage = emailErrorMessage.value
                            )
                            CustomTextField(
                                value = password,
                                label = "Password",
                                error = passwordError.value,
                                errorMessage = passwordErrorMessage.value
                            )
                            Text(
                                "Forgot Password?",
                                style = TextStyle(
                                    color = primaryColor,
                                    fontWeight = FontWeight.Bold
                                ),
                                textDecoration = TextDecoration.Underline
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            if (isLoading.value) Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) { CircularProgressIndicator(color = primaryColor) } else CustomButton(
                                onClick = {
                                    if (email.value.isEmpty()) {
                                        emailError.value = true
                                        emailErrorMessage.value = "Email is required"
                                    }
                                    if (password.value.isEmpty()) {
                                        passwordError.value = true
                                        passwordErrorMessage.value = "Password is required"
                                    }
                                    if (email.value.isNotEmpty()) {
                                        emailError.value = false
                                        emailErrorMessage.value = ""
                                    }
                                    if (password.value.isNotEmpty()) {
                                        passwordError.value = false
                                    }

                                    if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                                        isLoading.value = true
                                        loginViewModel.login(
                                            email = email.value,
                                            password = password.value,
                                            onSuccess = {
                                                isLoading.value = false
                                                coroutineScope.launch {
                                                    snackbarHostState.showSnackbar("Login successful")
                                                }
                                                navController.navigate(NavigationRoutes.MainScreen) {
                                                    popUpTo(NavigationRoutes.SplashScreen) {
                                                        inclusive = true
                                                    }
                                                }
                                            },
                                            onFailure = {
                                                isLoading.value = false
                                                coroutineScope.launch {
                                                    snackbarHostState.showSnackbar(it.message.toString())
                                                }
                                            }
                                        )
                                    }
                                },
                                text = "Log in"
                            )
                        }
                    }
                    Image(
                        painter = painterResource(R.drawable.six),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize(0.6f)
                            .align(Alignment.TopCenter)
                            .offset(y = (-180).dp)
                    )
                }
            }
        }
    }
}


