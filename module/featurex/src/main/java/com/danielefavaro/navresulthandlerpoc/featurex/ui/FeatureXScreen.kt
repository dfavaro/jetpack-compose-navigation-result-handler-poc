package com.danielefavaro.navresulthandlerpoc.featurex.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danielefavaro.navresulthandlerpoc.ui.theme.NavResultHandlerPoCTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeatureXScreen(
    onCtaClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    BackHandler(onBack = onBackClick)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Feature X") },
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text("Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        ) {
            Text("Feature X", fontSize = 22.sp)
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
        FeatureXScreen(
            onCtaClick = {},
            onBackClick = {},
        )
    }
}