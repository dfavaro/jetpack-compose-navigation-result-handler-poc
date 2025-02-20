package com.danielefavaro.navresulthandlerpoc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.danielefavaro.navresulthandlerpoc.data.SharedPreferencesManager
import com.danielefavaro.navresulthandlerpoc.featurea.navigation.FeatureACallbacks
import com.danielefavaro.navresulthandlerpoc.featurea.navigation.FeatureANavController
import com.danielefavaro.navresulthandlerpoc.featurea.navigation.featureANavGraph
import com.danielefavaro.navresulthandlerpoc.featurea.navigation.rememberFeatureANavController
import com.danielefavaro.navresulthandlerpoc.featureb.navigation.FeatureBCallbacks
import com.danielefavaro.navresulthandlerpoc.featureb.navigation.FeatureBNavController
import com.danielefavaro.navresulthandlerpoc.featureb.navigation.featureBNavGraph
import com.danielefavaro.navresulthandlerpoc.featureb.navigation.rememberFeatureBNavController
import com.danielefavaro.navresulthandlerpoc.featurex.navigation.FeatureXNavController
import com.danielefavaro.navresulthandlerpoc.featurex.navigation.featureXNavGraph
import com.danielefavaro.navresulthandlerpoc.featurex.navigation.rememberFeatureXNavController
import com.danielefavaro.navresulthandlerpoc.ui.theme.NavResultHandlerPoCTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.conflate
import kotlinx.serialization.Serializable

@Serializable
private data object Home

private const val FEATURE_HOME_RESULT_KEY = "FEATURE_HOME_RESULT_KEY"

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

    val context = LocalContext.current
    val sharedPreferencesManager = remember {
        SharedPreferencesManager(context = context)
    }

    val subFeatureResult = remember {
        sharedPreferencesManager.observeKey(FEATURE_HOME_RESULT_KEY).conflate()
    }

    val featureXNavController: FeatureXNavController =
        rememberFeatureXNavController(navController = navController)

    val featureANavController: FeatureANavController =
        rememberFeatureANavController(navController = navController)

    val featureBNavController: FeatureBNavController =
        rememberFeatureBNavController(
            navController = navController,
            startSubFeature = featureXNavController::startForResult,
            subFeatureResult = subFeatureResult,
        )

    Column(
        Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        AppNavGraph(
            modifier = Modifier.weight(1f),
            navController = navController,
            featureANavController = featureANavController,
            featureBNavController = featureBNavController,
            featureXNavController = featureXNavController,
            sharedPreferencesManager = sharedPreferencesManager,
        )

        HorizontalDivider(modifier = Modifier.padding(16.dp))

        NavigationLog(
            modifier = Modifier.weight(1f),
            navController = navController,
        )
    }
}

@Composable
private fun AppNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    featureANavController: FeatureANavController,
    featureBNavController: FeatureBNavController,
    featureXNavController: FeatureXNavController,
    sharedPreferencesManager: SharedPreferencesManager,
) {
    Box(modifier = modifier) {
        NavHost(
            navController = navController,
            startDestination = Home,
        ) {
            composable<Home> { entry ->
                LaunchedEffect(Unit) {
                    featureXNavController.getResult(entry)?.let { result ->
                        // Handle result from FeatureX
                        println("FeatureX result: $result")
                        sharedPreferencesManager.putString(
                            FEATURE_HOME_RESULT_KEY,
                            result.toString()
                        )
                    }
                }

                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(onClick = featureANavController::startForResult) {
                        Text("Start FeatureA")
                    }
                    Button(onClick = featureBNavController::startFlow) {
                        Text("Start FeatureB")
                    }
                }
            }

            featureANavGraph(
                callbacks = object : FeatureACallbacks {
                    override val startSubFeature = featureXNavController::startForResult
                    override val getResult = featureXNavController::getResult
                    override val onBackClick: () -> Unit = featureANavController::dismiss
                },
            )
            featureBNavGraph(
                callbacks = object : FeatureBCallbacks {
                    override val onBackClick: () -> Unit = featureBNavController::dismiss
                },
            )
            featureXNavGraph(
                featureXNavController = featureXNavController,
            )
        }
    }
}

@Composable
private fun NavigationLog(
    modifier: Modifier,
    navController: NavController,
) {
    val state by navController.currentBackStack.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    LaunchedEffect(state) {
        listState.animateScrollToItem(state.size - 1)
    }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Navigation log", fontSize = 16.sp, fontWeight = FontWeight.Bold)

        LazyColumn(Modifier.fillMaxWidth(), state = listState) {
            items(state) { entry ->
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text("Route: ${entry.destination.route}", fontSize = 16.sp)
                }
            }
        }
    }
}