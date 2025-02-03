package com.danielefavaro.navresulthandlerpoc.featureB.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.danielefavaro.navresulthandlerpoc.featureB.ui.FeatureBScreen
import kotlin.random.Random

internal const val FEATURE_B_ROUTE = "featureB"

fun NavGraphBuilder.featureBNavGraph(
    featureBNavHelper: FeatureBNavHelper,
) {
    composable(FEATURE_B_ROUTE) {
        FeatureBScreen(
            onCtaClick = {
                // emulate result behavior
                featureBNavHelper as FeatureBNavHelperInternal

                featureBNavHelper.dismiss("Result from Feature B: ${Random.nextInt()}")
            }
        )
    }
}