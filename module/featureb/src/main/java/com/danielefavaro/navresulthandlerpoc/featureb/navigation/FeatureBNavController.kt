package com.danielefavaro.navresulthandlerpoc.featureb.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController


interface FeatureBNavController {
    fun startFlow()
    fun dismiss()

    @Composable
    fun ListenForSubFeaturesResults(entry: NavBackStackEntry)
}

interface FeatureBNavControllerInternal {
    fun navigateToScreenB(subFeatureResult: String?)
}

private val RESULT_OF_SUBFEATURE_KEY = "subFeatureKey"

interface SubFeatureConnector {
    val startSubFeature: (resultKey: String) -> Unit
    val getSubFeatureResult: (entry: NavBackStackEntry, key: String) -> String?
}

@Composable
fun rememberFeatureBNavController(
    navController: NavController,
    subFeatureConnector: SubFeatureConnector,
): FeatureBNavController =
    remember(navController) {
        FeatureBNavControllerImpl(
            navController = navController,
            subFeatureConnector = subFeatureConnector,
        )
    }


private class FeatureBNavControllerImpl(
    private val navController: NavController,
    private val subFeatureConnector: SubFeatureConnector,
) : ViewModel(), FeatureBNavController, FeatureBNavControllerInternal {

    override fun startFlow() {
        subFeatureConnector.startSubFeature(RESULT_OF_SUBFEATURE_KEY)
    }

    override fun dismiss() {
        // TODO popBackStack <T> fails :o
        navController.popBackStack(FeatureB, inclusive = true)
    }

    override fun navigateToScreenB(subFeatureResult: String?) {
        navController.navigate(FeatureB(arg = subFeatureResult)) {
            launchSingleTop = true
        }
    }

    @Composable
    override fun ListenForSubFeaturesResults(entry: NavBackStackEntry) {
        val subFeatureResultContainer: Any? by entry.savedStateHandle
            .getStateFlow(RESULT_OF_SUBFEATURE_KEY, null)
            .collectAsStateWithLifecycle()

        if (subFeatureResultContainer != null) {
            LaunchedEffect(Unit) {
                val subFeatureResult = subFeatureConnector.getSubFeatureResult(
                    entry,
                    RESULT_OF_SUBFEATURE_KEY
                )

                navigateToScreenB(subFeatureResult = subFeatureResult)
            }
        }
    }
}