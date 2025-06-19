package com.example.khizana.utilis

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.khizana.R
import com.example.khizana.ui.theme.primaryColor

@Composable
fun WarningDialog(
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    confirmText: String,
    showAlert: MutableState<Boolean>
) {
    if (showAlert.value) {
        AlertDialog(
            icon = {
                Icon(
                    painterResource(R.drawable.logowithoutbg),
                    contentDescription = "",
                    tint = primaryColor,
                    modifier = Modifier.height(65.dp)
                )
            },
            title = {
                Text(text = dialogTitle, textAlign = TextAlign.Center)
            },
            text = {
                Column {
                    Text(text = dialogText, textAlign = TextAlign.Center)
                }
            },
            onDismissRequest = {
                showAlert.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmation()
                    }
                ) {
                    Text(confirmText, color = primaryColor)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showAlert.value = false
                    }
                ) {
                    Text(stringResource(R.string.cancel), color = primaryColor)
                }
            }

        )
    }
}