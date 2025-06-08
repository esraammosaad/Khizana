package com.example.khizana.presentation.feature.products.view.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.khizana.R
import com.example.khizana.ui.theme.lighterGreyColor
import com.example.khizana.ui.theme.primaryColor

@Composable
fun CustomImagesRow(
    imageUris: MutableState<List<Any>?>,
    imageUri: MutableState<Any?>,
) {
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uri ->
            uri.let {
                imageUris.value = imageUris.value?.plus(it)
                imageUri.value = imageUris.value?.firstOrNull()

            }
        }
    )
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        items(imageUris.value?.size ?: 0) {
            Box(
                contentAlignment = Alignment.TopEnd
            ) {
                AsyncImage(
                    model = imageUris.value?.getOrNull(it) ?: "",
                    placeholder = painterResource(R.drawable.plus),
                    error = painterResource(R.drawable.plus),
                    contentDescription = "",
                    modifier = Modifier
                        .border(
                            color = lighterGreyColor,
                            width = 1.dp,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .size(100.dp)
                        .padding(8.dp)
                        .clickable {

                            imageUri.value = imageUris.value?.get(it)


                        },
                    contentScale = ContentScale.Fit
                )

                    Icon(
                        painter = painterResource(R.drawable.baseline_remove_24),
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            imageUris.value = imageUris.value?.minus(imageUris.value?.get(it) ?: "")
                            imageUri.value = imageUris.value?.firstOrNull()
                        }
                    )

            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        item {
            Image(
                painter = painterResource(R.drawable.plus),
                contentDescription = "",
                modifier = Modifier
                    .border(
                        color = lighterGreyColor,
                        width = 1.dp,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .size(100.dp)
                    .padding(8.dp)
                    .clickable {
                        galleryLauncher.launch("image/*")
                    }
            )
        }
    }
}
