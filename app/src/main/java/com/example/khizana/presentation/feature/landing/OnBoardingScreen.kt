package com.example.khizana.presentation.feature.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.khizana.R
import com.example.khizana.presentation.feature.authentication.viewModel.AuthViewModel
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.utilis.NavigationRoutes

@Composable
fun OnBoardingScreen(navController: NavController, authViewModel: AuthViewModel = hiltViewModel()) {

    LazyColumn(
        Modifier.padding(top = 64.dp, start = 16.dp, end = 16.dp),
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.bg),
                    contentDescription = "",
                    modifier = Modifier.size(40.dp),
                )
                Text(
                    stringResource(R.string.app_name), style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = 6.dp)) {
                Text(
                    stringResource(R.string.welcome_to_khizana_admin),
                    style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            Image(
                painter = painterResource(R.drawable.four),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(),
            )
            Text(
                stringResource(R.string.manage_your_store_with_ease_add_products_track_orders_and_stay_in_control_all_in_one_place),
                style = TextStyle(fontSize = 18.sp, color = Color.Gray)
            )
            Spacer(modifier = Modifier.height(28.dp))
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                stringResource(R.string.let_s_get_started),
                style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomButton(
                onClick = {
                    authViewModel.saveGetStartedState()
                    navController.navigate(NavigationRoutes.LoginScreen)
                },
                text = stringResource(R.string.get_started),
            )
        }
    }
}

@Composable
fun CustomButton(onClick: () -> Unit, text : String) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = primaryColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
    ) {
        Text(text, style = TextStyle(fontSize = 18.sp))
    }
}
