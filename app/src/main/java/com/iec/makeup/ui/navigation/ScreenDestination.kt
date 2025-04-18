package com.iec.makeup.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.iec.makeup.R
import kotlin.reflect.KClass

/**
 * Type for the top level destinations in the application. Contains metadata about the destination
 * that is used in the top app bar and common navigation UI.
 *
 * @param selectedIcon The icon to be displayed in the navigation UI when this destination is
 * selected.
 * @param unselectedIcon The icon to be displayed in the navigation UI when this destination is
 * not selected.
 * @param iconTextId Text that to be displayed in the navigation UI.
 * @param titleTextId Text that is displayed on the top app bar.
 * @param route The route to use when navigating to this destination.
 */
enum class TopLevelDestination(
    val selectedIcon: Int,
    val unSelectedIcon: Int,
    @StringRes val iconText: Int,
    @StringRes val titleTextId: Int,
    val route: String
) {
    Page1(
        selectedIcon = R.drawable.home_24dp_df9d9b_fill1_wght400_grad0_opsz24,
        unSelectedIcon = R.drawable.home_24dp_df9d9b_fill0_wght400_grad0_opsz24,
        iconText = R.string.page1,
        titleTextId = R.string.page1_title,
        route = Routes.MainHome.createRoute()
    ),
    Page2(
        selectedIcon = R.drawable.lens_blur_24dp_df9d9b_fill0_wght400_grad0_opsz24,
        unSelectedIcon = R.drawable.lens_blur_24dp_df9d9b_fill0_wght400_grad0_opsz24,
        iconText = R.string.page2,
        titleTextId = R.string.page2_title,
        route = Routes.Page2.createRoute()
    ),
    Page3(
        selectedIcon = R.drawable.shopping_cart_24dp_df9d9b_fill1_wght400_grad0_opsz24,
        unSelectedIcon = R.drawable.shopping_cart_24dp_df9d9b_fill0_wght400_grad0_opsz24,
        iconText = R.string.page3,
        titleTextId = R.string.page3_title,
        route = Routes.Page3.createRoute()
    ),
    Page4(
        selectedIcon = R.drawable.account_circle_24dp_df9d9b_fill1_wght400_grad0_opsz24,
        unSelectedIcon = R.drawable.account_circle_24dp_df9d9b_fill0_wght400_grad0_opsz24,
        iconText = R.string.page4,
        titleTextId = R.string.page4_title,
        route = Routes.Page4.createRoute()
    )

}

// Just rush, not yet implement type safe for this navigation.

sealed class Routes(
    val route: String
) {

    /*
    -- Route /auth --
     */
    // Authorise Routes
    data object Login : Routes("login") {
        fun createRoute() = "login"
    }

    data object Register : Routes("register") {
        fun createRoute() = "register"
    }

    /*
    -- Route /main/home --
     */
    data object MainHome : Routes("home") {
        fun createRoute() = "home"
    }

    data object MainNotification : Routes("home/notification") {
        fun createRoute() = "home/notification"
    }

    data object MainSearch : Routes("home/search") {
        fun createRoute() = "home/search"
    }

    data object MainAllMakeUp : Routes("home/all_makeup") {
        fun createRoute() = "home/all_makeup"
    }

    data object MainDetailMakeUp : Routes("home/detail/{${MAKE_UP_STYLIST_ID}}") {
        fun createRoute(makeupStylistID: String) = "home/detail/$makeupStylistID"
    }

    data object MainChatting : Routes("home/chat/{${MAKE_UP_STYLIST_ID}}") {
        fun createRoute(makeupStylistID: String) = "home/chat/$makeupStylistID"
    }

    data object MainAllMakeUpTemplate :
        Routes("home/all_makeup_template/{${MAKE_UP_CATEGORY_ID}}") {
        fun createRoute(makeupCategoryID: String) = "home/all_makeup_template/$makeupCategoryID"

    }

    data object MailDetailMakeUpTemplate :
        Routes("home/makeup_template_id/{${MAKE_UP_TEMPLATE_ID}}") {
        fun createRoute(makeupCategoryID: String) = "home/makeup_template_id/$makeupCategoryID"

    }

    /*
    -- Route /main/ai --
     */
    data object Page2 : Routes("analyze") {
        fun createRoute() = "analyze"
    }

    data object InstructionScreen : Routes("instruction") {
        fun createRoute() = "analyze/instruction"
    }

    data object ScreenChatWithAIRoute: Routes("chat_with_ai") {
        fun createRoute() = "chat_with_ai"
    }

    data object ScreenInteractionRoutes: Routes("interaction") {
        fun createRoute() = "interaction"
    }


    /*
    -- Route page3
     */
    data object Page3 : Routes("shopping") {
        fun createRoute() = "shopping"
    }

    data object Page4 : Routes("account") {
        fun createRoute() = "account"
    }

    companion object {
        const val MAKE_UP_STYLIST_ID = "makeup_stylist_id"
        const val MAKE_UP_CATEGORY_ID = "makeup_category_id"
        const val MAKE_UP_TEMPLATE_ID = "makeup_template_id"
    }
}