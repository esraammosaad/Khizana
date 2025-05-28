package com.example.khizana.presentation.feature.products.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.khizana.R
import com.example.khizana.domain.model.ProductsItem
import com.example.khizana.presentation.feature.products.viewModel.ProductsViewModel
import com.example.khizana.ui.theme.KhizanaTheme
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.ui.theme.secondaryColor

@Composable
fun ProductsScreen(modifier: Modifier = Modifier, productsViewModel: ProductsViewModel) {

    productsViewModel.getProducts()
    val products = productsViewModel.products.observeAsState().value?.products


       LazyColumn(
           modifier = Modifier
               .fillMaxSize()
               .padding(top = 0.dp, start = 20.dp, end = 20.dp),
       ) {

           items(products?.size ?: 0) { index ->
               Box(modifier = Modifier.padding(bottom = 16.dp)) {
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
                                   products?.get(index)?.image?.src ?: ""
                               ),
                               contentDescription = stringResource(R.string.product_image),
                               modifier = Modifier
                                   .width(120.dp)
                                   .fillMaxHeight()
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
                                   "ID: #${products?.get(index)?.id}",
                                   style = TextStyle(fontWeight = FontWeight.Bold)
                               )

                               Spacer(modifier = Modifier.height(8.dp))

                               Text(
                                   products?.get(index)?.title ?: "",
                                   style = TextStyle(fontSize = 16.sp),
                                   maxLines = 1,
                                   overflow = TextOverflow.Ellipsis
                               )

                               Spacer(modifier = Modifier.height(8.dp))

                               Text(
                                   products?.get(index)?.body_html ?: "",
                                   style = TextStyle(color = Color.LightGray),
                                   maxLines = 3,
                                   overflow = TextOverflow.Ellipsis
                               )

                               Spacer(modifier = Modifier.height(8.dp))

                               Text(
                                   "${products?.get(index)?.variants?.get(0)?.price} EGP",
                                   style = TextStyle()
                               )

                               Spacer(modifier = Modifier.height(8.dp))

                               CustomProductInfoRow(products?.get(index))


                           }
                       }


                   }
                   val status = products?.get(index)?.status
                   Box(
                       modifier = Modifier
                           .padding(5.dp)
                           .height(25.dp)
                           .width(75.dp)
                           .background(
                               Color(if (status.equals("active")) 0xffe1f7e6 else 0xfffff7d9),
                               shape = RoundedCornerShape(20)
                           )
                           .padding(horizontal = 10.dp)
                           .align(Alignment.TopEnd),
                       contentAlignment = Alignment.Center
                   ) {


                       Text(
                           status ?: "",
                           style = TextStyle(
                               fontWeight = FontWeight.Bold,
                               color = Color(if (status.equals("active")) 0xff68d583 else 0xffffd946)
                           )
                       )

                   }


               }


           }
       }



}

@Composable
private fun CustomProductInfoRow(
    product: ProductsItem?,
) {
    Row {
        CustomInfoBox(text = "Quantity: ${product?.variants?.get(0)?.inventory_quantity}")

        Spacer(modifier = Modifier.width(8.dp))

        CustomInfoBox(text = product?.vendor ?: "")

    }
}

@Composable
private fun CustomInfoBox(
 text : String
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(
                secondaryColor,
                shape = RoundedCornerShape(20)
            )
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )
        )

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    KhizanaTheme {
        // ProductsScreen(productsViewModel = productsViewModel)
    }
}