package com.danielefavaro.navresulthandlerpoc.featureb.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.danielefavaro.navresulthandlerpoc.featureb.ui.FeatureBScreen
import kotlinx.serialization.Serializable

@Serializable
internal data class FeatureB(
    val arg: String?,
)

interface FeatureBCallbacks {
    val onBackClick: () -> Unit
}

fun NavGraphBuilder.featureBNavGraph(
    callbacks: FeatureBCallbacks,
) {
    composable<FeatureB> { entry ->
        val arg: String? = entry.toRoute()

        FeatureBScreen(
            arg = arg,
            onBackClick = callbacks.onBackClick,
        )
    }
}