package com.example.khizana.presentation.feature.authentication.view

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.khizana.R
import com.example.khizana.presentation.feature.landing.CustomButton
import com.example.khizana.presentation.feature.authentication.viewModel.AuthViewModel
import com.example.khizana.presentation.feature.products.view.components.CustomTextField
import com.example.khizana.ui.theme.offWhiteColor
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.utilis.NavigationRoutes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }
    val emailError = remember { mutableStateOf(false) }
    val emailErrorMessage = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val usernameError = remember { mutableStateOf(false) }
    val usernameErrorMessage = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf(false) }
    val passwordErrorMessage = remember { mutableStateOf("") }
    val hidePassword = remember { mutableStateOf(true) }
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current


    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
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
                        stringResource(R.string.hi_welcome_back_admin),
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
                                value = username,
                                label = stringResource(R.string.username),
                                error = usernameError.value,
                                errorMessage = usernameErrorMessage.value
                            )
                            CustomTextField(
                                value = email,
                                label = stringResource(R.string.email),
                                error = emailError.value,
                                errorMessage = emailErrorMessage.value
                            )
                            CustomTextField(
                                value = password,
                                label = stringResource(R.string.password),
                                error = passwordError.value,
                                errorMessage = passwordErrorMessage.value,
                                isPassword = hidePassword,
                                trailingIcon = {
                                    val image = if (hidePassword.value)
                                        R.drawable.baseline_visibility_24
                                    else
                                        R.drawable.baseline_visibility_off_24
                                    IconButton(onClick = {
                                        hidePassword.value = !hidePassword.value
                                    }) {
                                        Icon(
                                            painter = painterResource(id = image),
                                            contentDescription = null
                                        )
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            if (isLoading.value) Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) { CircularProgressIndicator(color = primaryColor) } else CustomButton(
                                onClick = {
                                    if (email.value.isEmpty()) {
                                        emailError.value = true
                                        emailErrorMessage.value =
                                            context.getString(R.string.email_is_required)
                                    } else {
                                        emailError.value = false
                                        emailErrorMessage.value = ""
                                    }
                                    if (password.value.isEmpty()) {
                                        passwordError.value = true
                                        passwordErrorMessage.value =
                                            context.getString(R.string.password_is_required)
                                    } else {
                                        passwordError.value = false
                                        passwordErrorMessage.value = ""
                                    }
                                    if (username.value.isEmpty()) {
                                        usernameError.value = true
                                        usernameErrorMessage.value =
                                            context.getString(R.string.username_is_required)
                                    } else {
                                        usernameError.value = false
                                        usernameErrorMessage.value = ""
                                    }
                                    if (email.value.isNotEmpty() && password.value.isNotEmpty() && username.value.isNotEmpty()) {
                                        isLoading.value = true
                                        authViewModel.login(
                                            email = email.value,
                                            password = password.value,
                                            onSuccess = {
                                                coroutineScope.launch {
                                                    snackBarHostState.showSnackbar(
                                                        context.getString(
                                                            R.string.login_successful
                                                        ))
                                                }
                                                isLoading.value = false
                                                navController.navigate(NavigationRoutes.MainScreen) {
                                                    popUpTo(NavigationRoutes.SplashScreen) {
                                                        inclusive = true
                                                    }
                                                }
                                                FirebaseAuth.getInstance().currentUser?.updateProfile(
                                                    UserProfileChangeRequest.Builder()
                                                        .setDisplayName(username.value).build()
                                                )
                                            },
                                            onFailure = {
                                                isLoading.value = false
                                                coroutineScope.launch {
                                                    snackBarHostState.showSnackbar(it.message.toString())
                                                }
                                            }
                                        )
                                    }
                                },
                                text = stringResource(R.string.log_in)
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


