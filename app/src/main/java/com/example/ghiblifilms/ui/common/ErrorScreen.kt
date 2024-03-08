package com.example.ghiblifilms.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ghiblifilms.R
import com.example.ghiblifilms.ui.theme.padding_24
import com.example.ghiblifilms.ui.theme.padding_8

@Composable
fun ErrorScreen(
    errorMessage: String,
    @DrawableRes errorImage: Int = R.drawable.ic_error,
    onclick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding_8),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.height(padding_24))
        Image(
            painter = painterResource(id = errorImage),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(padding_24))
        Button(onClick = { onclick() }) {
            Text(text = "Try Again")
        }
    }
}
