package com.iec.makeup.ui.navigation

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.iec.ui.feature.main.message.box_chat_message.ModernChatScreen
import com.iec.makeup.core.model.ui.MakeUpTemplateLayout
import com.iec.makeup.data.remote.api.ChatbotRequest
import com.iec.makeup.ui.MakeupAppState
import com.iec.makeup.ui.features.ai_makeup.InstructionScreen
import com.iec.makeup.ui.features.ai_makeup.VirtualScreen
import com.iec.makeup.ui.features.ai_makeup.screen_chat_with_ai.ScreenChatWithAI
import com.iec.makeup.ui.features.ai_makeup.screen_chat_with_ai.ScreenChatWithAIVM
import com.iec.makeup.ui.features.ai_makeup.screen_experts_recommend.ScreenExpertsRcmStateful
import com.iec.makeup.ui.features.ai_makeup.screen_make_instruct.ScreenMakeUpInstruction
import com.iec.makeup.ui.features.ai_makeup.screen_response_ai.InteractionScreenStateful
import com.iec.makeup.ui.features.authentication.login.LoginScreen
import com.iec.makeup.ui.features.authentication.register.RegisterScreen
import com.iec.makeup.ui.features.authentication.third_party_auth.GoogleAuthLoadingScreen
import com.iec.makeup.ui.features.home.HomeScreen
import com.iec.makeup.ui.features.home.screen_all_makeup.AllMakeUpScreen
import com.iec.makeup.ui.features.home.screen_all_makeup_template.ScreenAllMakeupTemplateOfCategoryStateful
import com.iec.makeup.ui.features.booking.BookingScreen
import com.iec.makeup.ui.features.booking.screen_finish_book.ScreenBookCompleted
import com.iec.makeup.ui.features.home.screen_detail_template_layout.ScreenDetailTemplateLayoutStateful
import com.iec.makeup.ui.features.home.screen_expert_detail_information.ProfileScreen
import com.iec.makeup.ui.features.home.screen_notification.NotificationContent
import com.iec.makeup.ui.features.home.screen_search.SearchScreen
import com.iec.makeup.ui.features.profiles.UserProfileScreenStateful
import com.iec.makeup.ui.navigation.NavigationArguments.ARG_INITIAL_PROMPT
import com.iec.makeup.ui.navigation.Routes.Companion.INTERACTION_IMAGE
import com.iec.makeup.ui.navigation.Routes.Companion.INTERACTION_MAKEUP_TYPE
import com.iec.makeup.ui.navigation.Routes.Companion.INTERACTION_PROMPT
import com.iec.makeup.ui.navigation.custom_nav_type.CustomNavType
import kotlinx.serialization.json.Json


object NavigationArguments {
    const val ARG_INITIAL_PROMPT = "initialPrompt"
}

const val ARG_INITIAL_LIST_PROMPT = "initialListPrompt"


@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun NavigationGraph(
    navController: NavHostController,
    appState: MakeupAppState
) {
    NavHost(navController = navController, startDestination = "auth") {

        composable(
            route = "makeup://login-success?code={tempCode}",
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "makeup://login-success?code={tempCode}"
                }
            ),
            arguments = listOf(
                navArgument("tempCode") { nullable = true }
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
                navBack = { navController.popBackStack() },
                token = it.arguments?.getString("tempCode")
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
                            restoreState = true
                        }
                    },
                    navToSearch = {
                        navController.navigate(Routes.MainSearch.createRoute()) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    navToAllMakeUpArtist = {
                        navController.navigate(Routes.MainAllMakeUp.createRoute()) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    navToPersonalInfo = { id ->
                        navController.navigate(Routes.MainDetailMakeUp.createRoute(id)) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    navToChatting = {
                        navController.navigate(Routes.MainChatting.createRoute("0")) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    navToAllTemplate = { title, it ->
                        navController.navigate(
                            Routes.MainAllMakeUpTemplate.createRoute(
                                title,
                                it
                            )
                        ) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    navToAI = {
                        navController.navigate(Routes.Page2.createRoute()) {
                            launchSingleTop = false
                            restoreState = true
                        }
                    },
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
                appState.setVisibleBottomNav(false)
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
                appState.setVisibleBottomNav(false)
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
                appState.setVisibleBottomNav(false)
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
                appState.setVisibleBottomNav(false)
                val idMakeUp = it.arguments?.getString(Routes.MAKE_UP_STYLIST_ID) ?: "0"
                ProfileScreen(
                    id = idMakeUp,
                    navBack = { navController.popBackStack() },
                    navToBookingScreen = { exId ->
                        navController.navigate(Routes.ScreenBookingExpert.createRoute(exId))
                    }
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
                    navArgument(Routes.MAKE_UP_CATEGORY_ID) { type = NavType.StringType },
                    navArgument(Routes.MAKE_UP_TITLE_ID) { type = NavType.StringType }
                )
            ) {
                appState.setVisibleBottomNav(false)
                val idCategory =
                    it.arguments?.getString(Routes.MAKE_UP_CATEGORY_ID)?.split(",") ?: emptyList()
                ScreenAllMakeupTemplateOfCategoryStateful(
                    navBack = {
                        navController.popBackStack()
                    },
                    categoryID = idCategory,
                    navToTemplateDetail = { id ->
                        navController.navigate(
                            Routes.MailDetailMakeUpTemplate.createRoute(
                                Uri.encode(
                                    id
                                )
                            )
                        )
                    },
                    title = it.arguments?.getString(Routes.MAKE_UP_TITLE_ID) ?: "Dự tiệc"
                )
            }
            composable(
                route = Routes.MailDetailMakeUpTemplate.route,
                arguments = listOf(
                    navArgument(Routes.MAKE_UP_TEMPLATE_ID) {
                        type = CustomNavType.MakeUpTemplateLayoutNavType
                    }
                )
            ) { it ->
                appState.setVisibleBottomNav(false)
                val idCategory = it.arguments?.getString(Routes.MAKE_UP_TEMPLATE_ID)
                val makeUpLayout = idCategory?.let { layout ->
                    Json.decodeFromString<MakeUpTemplateLayout>(layout)
                }
                // Pass id then query by this id, not pass the Item
                ScreenDetailTemplateLayoutStateful(
                    item = makeUpLayout!!,
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
                        navInteraction = { prompt, image, id ->
                            navController.navigate(
                                Routes.ScreenInteractionRoutes.createRoute(
                                    prompt,
                                    image,
                                    id
                                )
                            )
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

                composable(
                    route = Routes.ScreenInteractionRoutes.route,
                    arguments = listOf(
                        navArgument(INTERACTION_PROMPT) { type = NavType.StringType },
                        navArgument(INTERACTION_IMAGE) { type = NavType.StringType },
                        navArgument(INTERACTION_MAKEUP_TYPE) { type = NavType.StringType }
                    )
                ) {
                    appState.setVisibleBottomNav(false)
                    val prompt = it.arguments?.getString(INTERACTION_PROMPT) ?: ""
                    val image = Uri.decode(it.arguments?.getString(INTERACTION_IMAGE) ?: "")
                    val makeupType = it.arguments?.getString(INTERACTION_MAKEUP_TYPE)
                        ?: "6802056530135d4049a8a6d4"
                    InteractionScreenStateful(
                        navBack = { navController.popBackStack() },
                        navToEditScreen = { id, image ->
                            navController.navigate(
                                Routes.ScreenChatWithAIRoute.createRoute(
                                    id,
                                    image
                                )
                            ) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        chatbotRequest = ChatbotRequest(
                            prompt = prompt,
                            imageRequest = image,
                            makeupTempId = makeupType
                        )
                    )
                }

                composable(
                    route = Routes.ScreenChatWithAIRoute.route,
                    arguments = listOf(
                        navArgument(Routes.CONVERSATION_ID) { type = NavType.StringType },
                        navArgument(Routes.IMAGE_INIT_ID) { type = NavType.StringType }
                    )
                ) {
                    appState.setVisibleBottomNav(false)
                    val id = it.arguments?.getString(Routes.CONVERSATION_ID) ?: ""
                    val image = Uri.decode(it.arguments?.getString(Routes.IMAGE_INIT_ID) ?: "")
                    val parentEntry = remember { navController.getBackStackEntry("main") }
                    val viewModel: ScreenChatWithAIVM = hiltViewModel(parentEntry)
                    ScreenChatWithAI(
                        navBack = { navController.popBackStack() },
                        navHome = {
                            navController.navigate(Routes.Page2.createRoute()) {
                                popUpTo(Routes.Page2.createRoute()) {
                                    inclusive = false
                                }
                                launchSingleTop = true
                                restoreState = false
                            }
                        },
                        navToInstruction = {
                            navController.navigate(Routes.ScreenMakeUpInstruction.createRoute()) {
                                restoreState = true
                                launchSingleTop = true
                            }
                        },
                        navToExpertsRcm = { quesID ->
                            navController.navigate(
                                Routes.ScreenExpertsRecommended.createRoute(
                                    quesID
                                )
                            )
                        },
                        imageLink = image,
                        chatBotID = id,
                        viewModel = viewModel
                    )
                }

                composable(
                    route = Routes.ScreenMakeUpInstruction.route
                ) {
                    val parentEntry = remember { navController.getBackStackEntry("main") }
                    val viewModel: ScreenChatWithAIVM = hiltViewModel(parentEntry)
                    ScreenMakeUpInstruction(
                        navBack = { navController.popBackStack() },
                        viewModel = viewModel
                    )
                }

                composable(
                    route = Routes.ScreenExpertsRecommended.route,
                    arguments = listOf(
                        navArgument(Routes.QUESTION_ID) { type = NavType.StringType }
                    )
                ) {
                    val id = it.arguments?.getString(Routes.QUESTION_ID) ?: ""
                    ScreenExpertsRcmStateful(
                        navBack = { navController.popBackStack() },
                        questionId = id,
                        navToDetail = { expertID ->
                            navController.navigate(Routes.MainDetailMakeUp.createRoute(expertID))
                        }
                    )
                }


            }
            /*
             - Profile Route
             */
            navigation(
                startDestination = Routes.ScreenUserProfile.route,
                route = "profile"
            ) {
                composable(
                    route = Routes.ScreenUserProfile.route
                ) {
                    UserProfileScreenStateful(
                        navToLogin = {
                            navController.navigate("auth") {
                                popUpTo("main") {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }

            navigation(
                startDestination = Routes.ScreenBookingExpert.route,
                route = "main-booking"
            ) {
                composable(
                    route = Routes.ScreenBookingExpert.route
                ) {
                    BookingScreen(
                        navBack = { navController.popBackStack() },
                        navToComplete = {
                            navController.navigate("complete-booking") {
                                popUpTo("main") {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }
                composable(
                    route = "complete-booking",
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
                    }
                ) {
                    ScreenBookCompleted(
                        navHome = {
                            navController.navigate("main") {
                                popUpTo("main") {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    }

}