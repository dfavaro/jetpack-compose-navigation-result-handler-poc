package com.danielefavaro.navresulthandlerpoc.featureA.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.danielefavaro.navresulthandlerpoc.featureA.ui.FeatureAScreen
import com.danielefavaro.navresulthandlerpoc.featureB.navigation.FeatureBNavHelper

internal const val FEATURE_A_ROUTE = "featureA"

fun NavGraphBuilder.featureANavGraph(
    featureBNavHelper: FeatureBNavHelper,
) {
    composable(FEATURE_A_ROUTE) {
        val state by featureBNavHelper.result.collectAsStateWithLifecycle()

        FeatureAScreen(
            state = state,
            onCtaClick = featureBNavHelper::startForResult,
        )
    }
}