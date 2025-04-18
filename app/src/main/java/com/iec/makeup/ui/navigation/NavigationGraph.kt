package com.iec.makeup.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.iec.ui.feature.main.message.box_chat_message.ModernChatScreen
import com.iec.makeup.core.model.ui.MakeUpTemplateLayout
import com.iec.makeup.core.model.ui.mockMakeUpTemplateLayout
import com.iec.makeup.ui.MakeupAppState
import com.iec.makeup.ui.features.ai_makeup.InstructionScreen
import com.iec.makeup.ui.features.ai_makeup.VirtualScreen
import com.iec.makeup.ui.features.ai_makeup.screen_chat_with_ai.ScreenChatWithAI
import com.iec.makeup.ui.features.ai_makeup.screen_response_ai.InteractionScreen
import com.iec.makeup.ui.features.ai_makeup.screen_response_ai.InteractionScreenStateful
import com.iec.makeup.ui.features.authentication.login.LoginScreen
import com.iec.makeup.ui.features.authentication.register.RegisterScreen
import com.iec.makeup.ui.features.authentication.third_party_auth.GoogleAuthLoadingScreen
import com.iec.makeup.ui.features.home.HomeScreen
import com.iec.makeup.ui.features.home.screen_notification.NotificationContent
import com.iec.makeup.ui.features.home.screen_search.SearchScreen
import com.iec.makeup.ui.features.home.screen_all_makeup.AllMakeUpScreen
import com.iec.makeup.ui.features.home.screen_all_makeup_template.ScreenAllMakeupTemplateOfCategoryStateful
import com.iec.makeup.ui.features.home.screen_detail_template_layout.ScreenDetailTemplateLayout
import com.iec.makeup.ui.features.home.screen_detail_template_layout.ScreenDetailTemplateLayoutStateful
import com.iec.makeup.ui.features.home.screen_makeup_info.ProfileScreen
import com.iec.makeup.ui.navigation.NavigationArguments.ARG_INITIAL_PROMPT


object NavigationArguments {
    const val ARG_INITIAL_PROMPT = "initialPrompt"
}

const val ARG_INITIAL_LIST_PROMPT = "initialListPrompt"


@Composable
fun NavigationGraph(
    navController: NavHostController,
    appState: MakeupAppState
) {
    NavHost(navController = navController, startDestination = "auth") {

        composable(
            route = "login-success?code={tempCode}",
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "makeup://login-success?code={tempCode}"
                }
            ),
            arguments = listOf(
                navArgument("code") { nullable = true }
            )
        ) {
            appState.setVisibleBottomNav(false)
            GoogleAuthLoadingScreen(
                navToHome = {
                    navController.navigate("main") {
                        popUpTo("auth") {
                            inclusive = false
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                },
                navBack = { navController.popBackStack() }
            )
        }

        navigation(
            startDestination = Routes.Login.route,
            route = "auth"
        ) {
            composable(
                route = Routes.Login.createRoute(),
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(100)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(100)
                    )
                }) {

                appState.setVisibleBottomNav(false)
                LoginScreen(
                    navToRegister = {
                        navController.navigate(Routes.Register.createRoute()) {
                            launchSingleTop = true
                            restoreState = false
                        }
                    },
                    navToHome = {
                        navController.navigate("main") {
                            popUpTo("auth") {
                                inclusive = false
                            }
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                )
            }
            composable(route = Routes.Register.createRoute()) {

                appState.setVisibleBottomNav(false)
                RegisterScreen(
                    navBack = { navController.popBackStack() }
                )
            }
        }
        // For type-safety, since the project is current in development then we will not implement this
        navigation(
            startDestination = Routes.MainHome.route,
            route = "main"
        ) {
            /*
              - Main Route
             */
            composable(route = Routes.MainHome.route) {
                appState.setVisibleBottomNav(true)
                HomeScreen(
                    navToNotification = {
                        navController.navigate(Routes.MainNotification.createRoute()) {
                            launchSingleTop = true
                            restoreState = false
                        }
                    },
                    navToSearch = {
                        navController.navigate(Routes.MainSearch.createRoute()) {
                            launchSingleTop = true
                            restoreState = false
                        }
                    },
                    navToAllMakeUpArtist = {
                        navController.navigate(Routes.MainAllMakeUp.createRoute()) {
                            launchSingleTop = true
                            restoreState = false
                        }
                    },
                    navToPersonalInfo = { id ->
                        navController.navigate(Routes.MainDetailMakeUp.createRoute(id)) {
                            launchSingleTop = true
                            restoreState = false
                        }
                    },
                    navToChatting = {
                        navController.navigate(Routes.MainChatting.createRoute("0")) {
                            launchSingleTop = true
                            restoreState = false
                        }
                    },
                    navToAllTemplate = {
                        navController.navigate(Routes.MainAllMakeUpTemplate.createRoute(it)) {
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                )
            }
            composable(
                route = Routes.MainNotification.createRoute(),
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(100)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(100)
                    )
                }) {
                appState.setVisibleBottomNav(true)
                NotificationContent(
                    navBack = { navController.popBackStack() }
                )
            }
            composable(
                route = Routes.MainSearch.route,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(100)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(100)
                    )
                }) {
                appState.setVisibleBottomNav(true)
                SearchScreen(
                    navBack = { navController.popBackStack() }
                )
            }
            composable(
                route = Routes.MainAllMakeUp.route,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(100)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(100)
                    )
                }) {
                AllMakeUpScreen(
                    navBack = { navController.popBackStack() },
                    navToDetail = {
                        navController.navigate(Routes.MainDetailMakeUp.createRoute(it))
                    }
                )
            }

            composable(
                route = Routes.MainDetailMakeUp.route,
                arguments = listOf(
                    navArgument(Routes.MAKE_UP_STYLIST_ID) { type = NavType.StringType }
                )
            ) {
                val idMakeUp = it.arguments?.getString(Routes.MAKE_UP_STYLIST_ID) ?: "0"
                ProfileScreen(
                    id = idMakeUp,
                    navBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Routes.MainChatting.route,
                arguments = listOf(
                    navArgument(Routes.MAKE_UP_STYLIST_ID) { type = NavType.StringType }
                )
            ) {
                appState.setVisibleBottomNav(false)
                val idMakeUp = it.arguments?.getString(Routes.MAKE_UP_STYLIST_ID) ?: "0"
                ModernChatScreen(
                    userName = idMakeUp,
                    onBackPress = { navController.popBackStack() }
                )
            }

            composable(
                route = Routes.MainAllMakeUpTemplate.route,
                arguments = listOf(
                    navArgument(Routes.MAKE_UP_CATEGORY_ID) { type = NavType.StringType }
                )
            ) {
                appState.setVisibleBottomNav(true)
                val idCategory = it.arguments?.getString(Routes.MAKE_UP_CATEGORY_ID) ?: "0"
                ScreenAllMakeupTemplateOfCategoryStateful(
                    navBack = {
                        navController.popBackStack()
                    },
                    categoryID = idCategory,
                    navToTemplateDetail = { id ->
                        navController.navigate(Routes.MailDetailMakeUpTemplate.createRoute(id))
                    }
                )
            }
            composable(
                route = Routes.MailDetailMakeUpTemplate.route,
                arguments = listOf(
                    navArgument(Routes.MAKE_UP_TEMPLATE_ID) { type = NavType.StringType }
                )
            ) {
                appState.setVisibleBottomNav(true)
                val idCategory = it.arguments?.getString(Routes.MAKE_UP_TEMPLATE_ID) ?: "0"
                // Pass id then query by this id, not pass the Item
                ScreenDetailTemplateLayoutStateful(
                    item = mockMakeUpTemplateLayout[0],
                    onApplyTemplate = {
                        navController.navigate(Routes.Page2.createRoute())
                    },
                    onClose = {
                        navController.popBackStack()
                    }
                )
            }

            /*
             - AI_Screen Route
             */

            navigation(
                startDestination = Routes.Page2.createRoute(),
                route = "ai",
                arguments = listOf(
                    navArgument(ARG_INITIAL_PROMPT) { type = NavType.StringType },
                    navArgument(ARG_INITIAL_LIST_PROMPT) {
                        type = NavType.StringListType
                    }
                )
            ) {
                composable(route = Routes.Page2.createRoute()) {
                    appState.setVisibleBottomNav(true)
                    val initialPrompt: String = it.arguments?.getString(ARG_INITIAL_PROMPT) ?: ""
                    val initialListPrompt: List<String>? =
                        it.arguments?.getStringArrayList(ARG_INITIAL_LIST_PROMPT)
                    VirtualScreen(
                        initialPrompts = initialPrompt,
                        randomList = initialListPrompt,
                        navBack = { navController.popBackStack() },
                        navInstruction = { navController.navigate(Routes.InstructionScreen.createRoute()) },
                        navInteraction = {
                            navController.navigate(Routes.ScreenInteractionRoutes.route)
                        }
                    )
                }

                composable(route = Routes.InstructionScreen.createRoute()) {
                    appState.setVisibleBottomNav(false)
                    InstructionScreen(
                        navBack = { navController.popBackStack() },
                        navLaunchScreen = {
                            navController.navigate(Routes.Page2.createRoute()) {
                                launchSingleTop = true
                                restoreState = false
                            }
                        }
                    )
                }

                composable(route = Routes.ScreenInteractionRoutes.route) {
                    appState.setVisibleBottomNav(false)
                    InteractionScreenStateful(
                        navBack = { navController.popBackStack() },
                        navToEditScreen = {
                            navController.navigate(Routes.ScreenChatWithAIRoute.createRoute()) {
                            }
                        }
                    )
                }

                composable(route = Routes.ScreenChatWithAIRoute.route) {
                    appState.setVisibleBottomNav(false)
                    ScreenChatWithAI(
                        navBack = { navController.popBackStack() }
                    )
                }

            }


            /*
             - Cart Route
             */
            composable(route = Routes.Page3.createRoute()) {
                appState.setVisibleBottomNav(true)
                Text("Page 3")
            }


            /*
             - Profile Route
             */
            composable(route = Routes.Page4.createRoute()) {
                appState.setVisibleBottomNav(true)
                Text("Page 4")
            }
        }
    }

}