package com.danielefavaro.navresulthandlerpoc.featurea.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

interface FeatureANavController {
    fun startForResult()
    fun getResult(entry: NavBackStackEntry): String?
    fun dismiss()
}

internal interface FeatureANavControllerInternal {
    fun setResult(res: String)
}

@Composable
fun rememberFeatureANavController(
    navController: NavController,
): FeatureANavController {

    return FeatureANavControllerImpl(
        navController = navController,
    )
}

private class FeatureANavControllerImpl(
    private val navController: NavController,
) : ViewModel(), FeatureANavController,
    FeatureANavControllerInternal {

    override fun startForResult() {
        navController.navigate(FeatureA) {
            launchSingleTop = true
        }
    }

    override fun getResult(entry: NavBackStackEntry): String? {
        return entry.savedStateHandle.remove<String?>(FEATURE_A_RESULT_KEY)
    }

    override fun setResult(res: String) {
        navController.previousBackStackEntry?.savedStateHandle?.set(FEATURE_A_RESULT_KEY, res)
    }

    override fun dismiss() {
        navController.popBackStack(FeatureA, inclusive = true)
    }
}