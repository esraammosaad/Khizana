package com.example.khizana.utilis

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.khizana.R
import com.example.khizana.ui.theme.primaryColor

@Composable
fun ConfirmationDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = { onDismiss() },
            title = {
                Text(text = stringResource(R.string.confirmation))
            },
            text = {
                Text(stringResource(R.string.are_you_sure_you_want_to_delete_this_product))
            },
            confirmButton = {
                TextButton(onClick = {
                    onConfirm()
                    onDismiss()
                }) {
                    Text(stringResource(R.string.delete), color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(stringResource(R.string.cancel), color = primaryColor)
                }
            }
        )
    }
}
