package com.danielefavaro.navresulthandlerpoc.featurea.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.danielefavaro.navresulthandlerpoc.featurea.ui.FeatureAEvent
import com.danielefavaro.navresulthandlerpoc.featurea.ui.FeatureAScreen
import com.danielefavaro.navresulthandlerpoc.featurea.ui.FeatureAState
import com.danielefavaro.navresulthandlerpoc.featurea.ui.FeatureAViewModel
import kotlinx.serialization.Serializable

@Serializable
internal data object FeatureA

internal const val FEATURE_A_RESULT_KEY = "resultA"

interface FeatureACallbacks {
    val startSubFeature: () -> Unit
    val getResult: (entry: NavBackStackEntry) -> String?
    val onBackClick: () -> Unit
}

fun NavGraphBuilder.featureANavGraph(
    callbacks: FeatureACallbacks
) {
    composable<FeatureA> { entry ->
        val viewModel: FeatureAViewModel = hiltViewModel()
        val state: FeatureAState by viewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            callbacks.getResult(entry).let(viewModel::consumeArg)

            when (state.event) {
                FeatureAEvent.OnArgConsumed -> viewModel::onEventConsumed
                FeatureAEvent.None -> Unit
            }
        }

        FeatureAScreen(
            arg = state.arg,
            onCtaClick = callbacks.startSubFeature,
            onBackClick = callbacks.onBackClick
        )
    }
}