package com.danielefavaro.navresulthandlerpoc.featureB.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

interface FeatureBNavHelper {
    val result: StateFlow<String>

    fun startForResult()
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

    override val result: StateFlow<String> = featureBResultHelper.result

    override fun startForResult() {
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
internal class FeatureBResultHelper @Inject constructor() :
    ViewModel(),
    FeatureBNavHelperInternal {

    private val _result: MutableStateFlow<String> = MutableStateFlow("")
    val result: StateFlow<String> = _result.asStateFlow()

    override fun dismiss(res: String) {
        _result.update { res }
    }
}