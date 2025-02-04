package com.danielefavaro.navresulthandlerpoc.featureB.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.danielefavaro.navresulthandlerpoc.featureB.ui.FeatureBScreen
import kotlin.random.Random

internal const val FEATURE_B_ROUTE = "featureB"
internal const val FEATURE_B_ARG_KEY = "result"

fun NavGraphBuilder.featureBNavGraph(
    featureBNavHelper: FeatureBNavHelper,
) {
    composable(FEATURE_B_ROUTE) {
        FeatureBScreen(
            onCtaClick = {
                featureBNavHelper as FeatureBNavHelperInternal

                featureBNavHelper.setResult(Random.nextInt())
                featureBNavHelper.dismiss()
            }
        )
    }
}