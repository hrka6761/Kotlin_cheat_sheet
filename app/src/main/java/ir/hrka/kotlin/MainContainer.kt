package ir.hrka.kotlin

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ir.hrka.kotlin.core.Constants.COROUTINE_TOPICS_SCREEN_ARGUMENT_VERSION_NAME
import ir.hrka.kotlin.core.Constants.COROUTINE_TOPICS_SCREEN_ARGUMENT_VERSION_SUFFIX
import ir.hrka.kotlin.core.Constants.COROUTINE_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_ID
import ir.hrka.kotlin.core.Constants.COROUTINE_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_NAME
import ir.hrka.kotlin.core.Constants.COROUTINE_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_STATE_NAME
import ir.hrka.kotlin.core.Constants.KOTLIN_TOPICS_SCREEN_ARGUMENT_VERSION_NAME
import ir.hrka.kotlin.core.Constants.KOTLIN_TOPICS_SCREEN_ARGUMENT_VERSION_SUFFIX
import ir.hrka.kotlin.core.Constants.HOME_SCREEN_ARGUMENT_VERSION_NAME
import ir.hrka.kotlin.core.Constants.HOME_SCREEN_ARGUMENT_VERSION_SUFFIX
import ir.hrka.kotlin.core.Constants.KOTLIN_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_ID
import ir.hrka.kotlin.core.Constants.KOTLIN_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_NAME
import ir.hrka.kotlin.core.Constants.KOTLIN_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_STATE_NAME
import ir.hrka.kotlin.core.utilities.Screen.Splash
import ir.hrka.kotlin.core.utilities.Screen.Home
import ir.hrka.kotlin.core.utilities.Screen.KotlinTopics
import ir.hrka.kotlin.core.utilities.Screen.CoroutineTopics
import ir.hrka.kotlin.core.utilities.Screen.KotlinTopicPoints
import ir.hrka.kotlin.core.utilities.Screen.CoroutineTopicPoints
import ir.hrka.kotlin.core.utilities.Screen.About
import ir.hrka.kotlin.ui.screens.about.AboutScreen
import ir.hrka.kotlin.ui.screens.coroutine.CoroutineTopicsScreen
import ir.hrka.kotlin.ui.screens.coroutine_topic_points.CoroutineTopicPointsScreen
import ir.hrka.kotlin.ui.screens.kotlin_topic_points.KotlinTopicPointsScreen
import ir.hrka.kotlin.ui.screens.kotlin.KotlinTopicsScreen
import ir.hrka.kotlin.ui.screens.home.HomeScreen
import ir.hrka.kotlin.ui.screens.splash.SplashScreen
import ir.hrka.kotlin.ui.theme.KotlinTheme

@Composable
fun AppContent() {
    val navHostController = rememberNavController()
    val activity = (LocalContext.current as MainActivity)

    KotlinTheme {
        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navHostController,
            startDestination = Splash()
        ) {
            composable(
                route = Splash()
            ) {
                SplashScreen(activity, navHostController)
            }
            composable(
                route = "${Home()}/{${HOME_SCREEN_ARGUMENT_VERSION_NAME}}/{${HOME_SCREEN_ARGUMENT_VERSION_SUFFIX}}",
                arguments = listOf(
                    navArgument(HOME_SCREEN_ARGUMENT_VERSION_NAME) {
                        type = NavType.StringType
                    },
                    navArgument(HOME_SCREEN_ARGUMENT_VERSION_SUFFIX) {
                        type = NavType.StringType
                    },
                )
            ) { backStackEntry ->

                val gitVersionName =
                    backStackEntry.arguments?.getString(HOME_SCREEN_ARGUMENT_VERSION_NAME)
                val gitVersionSuffix =
                    backStackEntry.arguments?.getString(HOME_SCREEN_ARGUMENT_VERSION_SUFFIX)

                HomeScreen(activity, navHostController, gitVersionName, gitVersionSuffix)
            }
            composable(
                route = "${KotlinTopics()}/{${KOTLIN_TOPICS_SCREEN_ARGUMENT_VERSION_NAME}}/{${KOTLIN_TOPICS_SCREEN_ARGUMENT_VERSION_SUFFIX}}",
                arguments = listOf(
                    navArgument(KOTLIN_TOPICS_SCREEN_ARGUMENT_VERSION_NAME) {
                        type = NavType.StringType
                    },
                    navArgument(KOTLIN_TOPICS_SCREEN_ARGUMENT_VERSION_SUFFIX) {
                        type = NavType.StringType
                    },
                )
            ) { backStackEntry ->

                val gitVersionName =
                    backStackEntry.arguments?.getString(KOTLIN_TOPICS_SCREEN_ARGUMENT_VERSION_NAME)
                val gitVersionSuffix =
                    backStackEntry.arguments?.getString(KOTLIN_TOPICS_SCREEN_ARGUMENT_VERSION_SUFFIX)

                KotlinTopicsScreen(activity, navHostController, gitVersionName, gitVersionSuffix)
            }
            composable(
                route = "${CoroutineTopics()}/{${COROUTINE_TOPICS_SCREEN_ARGUMENT_VERSION_NAME}}/{${COROUTINE_TOPICS_SCREEN_ARGUMENT_VERSION_SUFFIX}}",
                arguments = listOf(
                    navArgument(COROUTINE_TOPICS_SCREEN_ARGUMENT_VERSION_NAME) {
                        type = NavType.StringType
                    },
                    navArgument(COROUTINE_TOPICS_SCREEN_ARGUMENT_VERSION_SUFFIX) {
                        type = NavType.StringType
                    },
                )
            ) { backStackEntry ->

                val gitVersionName =
                    backStackEntry.arguments?.getString(KOTLIN_TOPICS_SCREEN_ARGUMENT_VERSION_NAME)
                val gitVersionSuffix =
                    backStackEntry.arguments?.getString(KOTLIN_TOPICS_SCREEN_ARGUMENT_VERSION_SUFFIX)

                CoroutineTopicsScreen(activity, navHostController, gitVersionName, gitVersionSuffix)
            }
            composable(
                route = "${KotlinTopicPoints()}/{${KOTLIN_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_NAME}}/{${KOTLIN_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_STATE_NAME}}/{${KOTLIN_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_ID}}",
                arguments = listOf(
                    navArgument(KOTLIN_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_NAME) {
                        type = NavType.StringType
                    },
                    navArgument(KOTLIN_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_STATE_NAME) {
                        type = NavType.StringType
                    },
                    navArgument(KOTLIN_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_ID) {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val kotlinTopicName =
                    backStackEntry.arguments?.getString(
                        KOTLIN_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_NAME
                    ) ?: ""
                val hasContentUpdated =
                    backStackEntry.arguments?.getString(
                        KOTLIN_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_STATE_NAME
                    ) ?: false
                val kotlinTopicId =
                    backStackEntry.arguments?.getInt(
                        KOTLIN_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_ID
                    ) ?: -1

                KotlinTopicPointsScreen(
                    activity,
                    navHostController,
                    kotlinTopicName,
                    kotlinTopicId,
                    hasContentUpdated == "true"
                )
            }
            composable(
                route = "${CoroutineTopicPoints()}/{${COROUTINE_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_NAME}}/{${COROUTINE_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_STATE_NAME}}/{${COROUTINE_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_ID}}",
                arguments = listOf(
                    navArgument(COROUTINE_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_NAME) {
                        type = NavType.StringType
                    },
                    navArgument(COROUTINE_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_STATE_NAME) {
                        type = NavType.StringType
                    },
                    navArgument(COROUTINE_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_ID) {
                        type = NavType.IntType
                    },
                )
            ) { backStackEntry ->

                val kotlinTopicName =
                    backStackEntry.arguments?.getString(
                        COROUTINE_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_NAME
                    ) ?: ""
                val hasContentUpdated =
                    backStackEntry.arguments?.getString(
                        COROUTINE_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_STATE_NAME
                    ) ?: false
                val kotlinTopicId =
                    backStackEntry.arguments?.getInt(
                        COROUTINE_TOPIC_POINTS_SCREEN_ARGUMENT_KOTLIN_TOPIC_ID
                    ) ?: -1

                CoroutineTopicPointsScreen(
                    activity,
                    navHostController,
                    kotlinTopicName,
                    kotlinTopicId,
                    hasContentUpdated == "true"
                )
            }
            composable(
                route = About()
            ) {
                AboutScreen(activity, navHostController)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AppContentPreview(modifier: Modifier = Modifier) {
    AppContent()
}