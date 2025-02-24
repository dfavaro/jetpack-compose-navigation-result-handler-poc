package com.danielefavaro.navresulthandlerpoc.featurex.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

interface FeatureXNavController {
    fun startForResult(resultKey: String = FEATURE_X_RESULT_KEY)
    fun getResult(entry: NavBackStackEntry, key: String = FEATURE_X_RESULT_KEY): String?
}

internal interface FeatureXNavControllerInternal {
    fun setResult(key: String, res: String)
    fun dismiss(arg: FeatureXArg)
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

    override fun startForResult(resultKey: String) {
        navController.navigate(FeatureXArg(resultKey)) {
            launchSingleTop = true
        }
    }

    override fun getResult(entry: NavBackStackEntry, key: String): String? {
        return entry.savedStateHandle.remove(key)
    }

    override fun setResult(key: String, res: String) {
        navController.previousBackStackEntry?.savedStateHandle?.set(key, res)
    }

    override fun dismiss(arg: FeatureXArg) {
        navController.popBackStack(arg, inclusive = true)
    }
}