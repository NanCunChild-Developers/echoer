package com.nancunchild.echoer.ui_components


import android.content.Intent
import android.graphics.drawable.Icon
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp


@Composable
fun wirelessMonitorBtn(
    icon: @Composable () -> Unit,
    headlineText: String = "",
    assetText: String = "",
    onClick: () -> Unit,
    wirelessActivationState: State<String> = mutableStateOf("Unknown"),
    wirelessConnectionState: State<String> = mutableStateOf("Unknown"),
) {
    ExtendedFloatingActionButton(
        icon = icon,
        text = {
            Column {
                Text(
                    text = headlineText +" "+ wirelessActivationState.value,
                    fontWeight = FontWeight.W500,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = assetText + wirelessConnectionState.value,
                    fontWeight = FontWeight.W200,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        onClick = onClick,
        containerColor = when (wirelessActivationState.value) {
            "ON" -> Color(0xFF1E90FF)
            "OFF" -> Color(0xFFD0D0D0)
            else -> Color(0xFFD0D0D0)
        },
        contentColor = Color(0xCCFFFFFF),
        modifier = Modifier
            .height(88.dp)
            .width(178.dp)
            .padding(6.dp)
    )
}
