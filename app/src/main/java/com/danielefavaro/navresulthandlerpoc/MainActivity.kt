package com.danielefavaro.navresulthandlerpoc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.danielefavaro.navresulthandlerpoc.featureA.navigation.FEATURE_A_ROUTE
import com.danielefavaro.navresulthandlerpoc.featureA.navigation.featureANavGraph
import com.danielefavaro.navresulthandlerpoc.featureB.navigation.FeatureBNavHelper
import com.danielefavaro.navresulthandlerpoc.featureB.navigation.featureBNavGraph
import com.danielefavaro.navresulthandlerpoc.featureB.navigation.rememberFeatureBNavHelper
import com.danielefavaro.navresulthandlerpoc.ui.theme.NavResultHandlerPoCTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavResultHandlerPoCTheme {
                App()
            }
        }
    }
}

@Composable
private fun App() {
    val navController = rememberNavController()

    val featureBNavHelper: FeatureBNavHelper =
        rememberFeatureBNavHelper(navController = navController)

    NavHost(
        navController = navController,
        startDestination = FEATURE_A_ROUTE,
    ) {
        featureANavGraph(
            featureBNavHelper = featureBNavHelper,
        )
        featureBNavGraph(
            featureBNavHelper = featureBNavHelper,
        )
    }
}