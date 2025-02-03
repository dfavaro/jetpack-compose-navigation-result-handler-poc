package com.danielefavaro.navresulthandlerpoc.featureB.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

interface FeatureBNavHelper {
    fun startForResult(onResult: (res: String) -> Unit = {})
}

internal interface FeatureBNavHelperInternal {
    fun dismiss(res: String)
}

@Composable
fun rememberFeatureBNavHelper(
    navController: NavController,
): FeatureBNavHelper {
    val featureBResultHelper: FeatureBResultHelper = hiltViewModel()

    return FeatureBNavHelperImpl(
        navController = navController,
        featureBResultHelper = featureBResultHelper,
    )
}

private class FeatureBNavHelperImpl(
    private val navController: NavController,
    private val featureBResultHelper: FeatureBResultHelper,
) : ViewModel(), FeatureBNavHelper,
    FeatureBNavHelperInternal {

    override fun startForResult(onResult: (String) -> Unit) {
        featureBResultHelper.startForResult(
            onResult = onResult,
        )

        navController.navigate(FEATURE_B_ROUTE) {
            launchSingleTop = true
        }
    }

    override fun dismiss(res: String) {
        featureBResultHelper.dismiss(res = res)

        navController.popBackStack(FEATURE_B_ROUTE, inclusive = true)
    }
}

@HiltViewModel
internal class FeatureBResultHelper @Inject constructor() : ViewModel(), FeatureBNavHelper,
    FeatureBNavHelperInternal {

    private var onResult: ((res: String) -> Unit)? = null

    override fun startForResult(onResult: (String) -> Unit) {
        this.onResult = onResult
    }

    override fun dismiss(res: String) {
        onResult?.invoke(res)
        onResult = null
    }

    override fun onCleared() {
        super.onCleared()
        onResult = null
    }
}