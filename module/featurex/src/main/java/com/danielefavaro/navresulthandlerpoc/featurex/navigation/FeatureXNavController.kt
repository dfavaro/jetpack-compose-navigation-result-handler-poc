package com.danielefavaro.navresulthandlerpoc.featurex.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

interface FeatureXNavController {
    fun startForResult()
    fun getResult(entry: NavBackStackEntry): String?
}

internal interface FeatureXNavControllerInternal {
    fun setResult(res: String)
    fun dismiss()
}

@Composable
fun rememberFeatureXNavController(
    navController: NavController,
): FeatureXNavController {

    return FeatureXNavControllerImpl(
        navController = navController,
    )
}

private class FeatureXNavControllerImpl(
    private val navController: NavController,
) : ViewModel(), FeatureXNavController,
    FeatureXNavControllerInternal {

    override fun startForResult() {
        navController.navigate(FeatureX) {
            launchSingleTop = true
        }
    }

    override fun getResult(entry: NavBackStackEntry): String? {
        return entry.savedStateHandle.remove<String?>(FEATURE_X_RESULT_KEY)
    }

    override fun setResult(res: String) {
        navController.previousBackStackEntry?.savedStateHandle?.set(FEATURE_X_RESULT_KEY, res)
    }

    override fun dismiss() {
        navController.popBackStack(FeatureX, inclusive = true)
    }
}