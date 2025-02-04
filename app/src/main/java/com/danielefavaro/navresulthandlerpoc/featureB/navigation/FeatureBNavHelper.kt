package com.danielefavaro.navresulthandlerpoc.featureB.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

interface FeatureBNavHelper {
    fun startForResult()
    fun getResult(entry: NavBackStackEntry): Int?
}

internal interface FeatureBNavHelperInternal {
    fun setResult(res: Int)
    fun dismiss()
}

@Composable
fun rememberFeatureBNavHelper(
    navController: NavController,
): FeatureBNavHelper {

    return FeatureBNavHelperImpl(
        navController = navController,
    )
}

private class FeatureBNavHelperImpl(
    private val navController: NavController,
) : ViewModel(), FeatureBNavHelper,
    FeatureBNavHelperInternal {

    override fun startForResult() {
        navController.navigate(FEATURE_B_ROUTE) {
            launchSingleTop = true
        }
    }

    override fun getResult(entry: NavBackStackEntry): Int? {
        return entry.savedStateHandle.get<Int?>(FEATURE_B_ARG_KEY)
    }

    override fun setResult(res: Int) {
        navController.previousBackStackEntry?.savedStateHandle?.set(FEATURE_B_ARG_KEY, res)
    }

    override fun dismiss() {
        navController.popBackStack(FEATURE_B_ROUTE, inclusive = true)
    }
}