package com.danielefavaro.navresulthandlerpoc.featureA.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.danielefavaro.navresulthandlerpoc.featureA.ui.FeatureAEvent
import com.danielefavaro.navresulthandlerpoc.featureA.ui.FeatureAScreen
import com.danielefavaro.navresulthandlerpoc.featureA.ui.FeatureAState
import com.danielefavaro.navresulthandlerpoc.featureA.ui.FeatureAViewModel
import com.danielefavaro.navresulthandlerpoc.featureB.navigation.FeatureBNavHelper

internal const val FEATURE_A_ROUTE = "featureA"

fun NavGraphBuilder.featureANavGraph(
    featureBNavHelper: FeatureBNavHelper,
) {
    composable(FEATURE_A_ROUTE) { entry ->
        val viewModel: FeatureAViewModel = hiltViewModel()
        val state: FeatureAState by viewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            featureBNavHelper.getResult(entry = entry).let(viewModel::consumeArg)

            when (state.event) {
                FeatureAEvent.OnArgConsumed -> viewModel::onEventConsumed
                FeatureAEvent.None -> Unit
            }
        }

        FeatureAScreen(
            arg = state.arg,
            onCtaClick = featureBNavHelper::startForResult,
        )
    }
}