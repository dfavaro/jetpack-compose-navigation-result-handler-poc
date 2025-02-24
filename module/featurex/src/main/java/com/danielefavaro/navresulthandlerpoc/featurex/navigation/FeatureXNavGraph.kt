package com.danielefavaro.navresulthandlerpoc.featurex.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.danielefavaro.navresulthandlerpoc.featurex.ui.FeatureXScreen
import kotlinx.serialization.Serializable
import kotlin.random.Random

const val FEATURE_X_RESULT_KEY = "resultX"

@Serializable
internal data class FeatureXArg(val resultKey:String)

fun NavGraphBuilder.featureXNavGraph(
    featureXNavController: FeatureXNavController,
) {
    composable<FeatureXArg> { entry->
        val arg : FeatureXArg = entry.toRoute()

        FeatureXScreen(
            onCtaClick = {
                featureXNavController as FeatureXNavControllerInternal
                featureXNavController.setResult(arg.resultKey, Random.nextInt().toString())
                featureXNavController.dismiss(arg)
            },
            onBackClick = {
                (featureXNavController as FeatureXNavControllerInternal).dismiss(arg)
            },
        )
    }
}