package com.example.khizana.presentation.feature.products.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.khizana.R
import com.example.khizana.domain.model.ProductsItem

@Composable
fun CustomProductCard(
    modifier: Modifier,
    it: ProductsItem?,
) {
    Card(
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(12))
            .height(250.dp)
            .fillMaxWidth(),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.Black,
            disabledContentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = rememberAsyncImagePainter(
                    it?.image?.src ?: ""
                ),
                contentDescription = stringResource(R.string.product_image),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(160.dp)
                    .padding(horizontal = 6.dp)
            )
            Column(
                Modifier
                    .weight(1f)
                    .padding(end = 8.dp, top = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    stringResource(R.string.id, it?.id ?: ""),
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    it?.title ?: "",
                    style = TextStyle(fontSize = 16.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    it?.body_html ?: "",
                    style = TextStyle(color = Color.LightGray),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "${it?.variants?.get(0)?.price} EGP",
                    style = TextStyle()
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomProductInfoRow(it)
            }
        }
    }
}

@Composable
private fun CustomProductInfoRow(
    product: ProductsItem?,
) {
    Row {
        CustomInfoBox(
            text = stringResource(
                R.string.quantity,
                product?.variants?.get(0)?.inventory_quantity ?: ""
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        CustomInfoBox(text = product?.vendor ?: "")
    }
}
