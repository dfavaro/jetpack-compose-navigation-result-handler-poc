package com.danielefavaro.navresulthandlerpoc.featureB.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danielefavaro.navresulthandlerpoc.ui.theme.NavResultHandlerPoCTheme

@Composable
fun FeatureBScreen(
    onCtaClick: () -> Unit,
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        ) {
            Text("Feature B", fontSize = 22.sp)
            Button(onClick = onCtaClick) {
                Text("Dismiss with result")
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NavResultHandlerPoCTheme {
        FeatureBScreen(
            onCtaClick = {}
        )
    }
}