package com.danielefavaro.navresulthandlerpoc.featurex.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.danielefavaro.navresulthandlerpoc.featurex.ui.FeatureXScreen
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
internal data object FeatureX

const val FEATURE_X_RESULT_KEY = "resultX"

fun NavGraphBuilder.featureXNavGraph(
    featureXNavController: FeatureXNavController,
) {
    composable<FeatureX> {
        FeatureXScreen(
            onCtaClick = {
                featureXNavController as FeatureXNavControllerInternal

                featureXNavController.setResult(Random.nextInt().toString())
                featureXNavController.dismiss()
            },
            onBackClick = (featureXNavController as FeatureXNavControllerInternal)::dismiss,
        )
    }
}