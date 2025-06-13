package com.example.khizana.presentation.feature.profile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.khizana.R
import com.example.khizana.presentation.feature.login.viewModel.AuthViewModel
import com.example.khizana.ui.theme.lightGreyColor
import com.example.khizana.ui.theme.lighterGreyColor
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.utilis.NavigationRoutes
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.size(160.dp)) {
                Image(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "",
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                        .border(1.dp, primaryColor, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = FirebaseAuth.getInstance().currentUser?.displayName ?: "",
            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold),
        )
        Text(
            text = FirebaseAuth.getInstance().currentUser?.email ?: "",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = lighterGreyColor
            ),
            modifier = Modifier.padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        Column {
            OptionRow(optionText = "Logout", onClick = {
                authViewModel.logout()
                navController.navigate(NavigationRoutes.LoginScreen,
                    builder = {
                        popUpTo(NavigationRoutes.SplashScreen) {
                            inclusive = true
                        }
                    }
                )
            }, icon = R.drawable.baseline_logout_24)
        }
    }
}


@Composable
fun OptionRow(
    optionText: String,
    onClick: () -> Unit,
    icon: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = lightGreyColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = optionText,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal)
            )
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = primaryColor
            )
        }
    }
}
