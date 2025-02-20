package com.danielefavaro.navresulthandlerpoc.featureb.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow

interface FeatureBNavController {
    fun startFlow()
    fun dismiss()
}

interface FeatureBNavControllerInternal {
    fun navigateToScreenB(subFeatureResult: String?)
}

@Composable
fun rememberFeatureBNavController(
    navController: NavController,
    startSubFeature: () -> Unit,
    subFeatureResult: Flow<String>,
): FeatureBNavController {
    val navImpl = FeatureBNavControllerImpl(
        navController = navController,
        startSubFeature = startSubFeature,
    )

    val subFeatureResult: String? by subFeatureResult.collectAsStateWithLifecycle(
        initialValue = null,
    )

    LaunchedEffect(subFeatureResult) {
        subFeatureResult?.let {
            navImpl.navigateToScreenB(
                subFeatureResult = subFeatureResult,
            )
        }
    }

    return navImpl
}

private class FeatureBNavControllerImpl(
    private val navController: NavController,
    private val startSubFeature: () -> Unit,
) : ViewModel(), FeatureBNavController, FeatureBNavControllerInternal {

    override fun startFlow() {
        startSubFeature()
    }

    override fun dismiss() {
        // TODO popBackStack <T> fails :o
        navController.popBackStack(FeatureB, inclusive = true)
    }

    override fun navigateToScreenB(subFeatureResult: String?) {
        navController.navigate(
            FeatureB(arg = subFeatureResult)
        ) {
            launchSingleTop = true
        }
    }
}