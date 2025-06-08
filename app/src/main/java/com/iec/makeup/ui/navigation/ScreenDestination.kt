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
        selectedIcon = R.drawable.ai,
        unSelectedIcon = R.drawable.ai,
        iconText = R.string.page2,
        titleTextId = R.string.page2_title,
        route = Routes.Page2.createRoute()
    ),
    Page4(
        selectedIcon = R.drawable.account_circle_24dp_df9d9b_fill1_wght400_grad0_opsz24,
        unSelectedIcon = R.drawable.account_circle_24dp_df9d9b_fill0_wght400_grad0_opsz24,
        iconText = R.string.page4,
        titleTextId = R.string.page4_title,
        route = Routes.ScreenUserProfile.createRoute()
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
        Routes("home/all_makeup_template/{$MAKE_UP_TITLE_ID}/{${MAKE_UP_CATEGORY_ID}}") {
        fun createRoute(title: String, makeupCategoryID: List<String>) = "home/all_makeup_template/$title/${makeupCategoryID.joinToString(",")}"

    }

    data object MailDetailMakeUpTemplate :
        Routes("home/makeup_template_id/{${MAKE_UP_TEMPLATE_ID}}") {
        fun createRoute(makeupCategoryID: String) = "home/makeup_template_id/$makeupCategoryID"
    }

    // ----------------------------------------------------------------------------
    /*
    -- Route /main/ai --
     */
    data object Page2 : Routes("analyze/{${ARG_INITIAL_PROMPT}}") {
        fun createRoute(initPrompt: String = "") = "analyze/$initPrompt"
    }

    data object InstructionScreen : Routes("instruction") {
        fun createRoute() = "analyze/instruction"
    }

    // Require image send to AI for the first message
    data object ScreenChatWithAIRoute: Routes("chat_with_ai/{${CONVERSATION_ID}}/{${IMAGE_INIT_ID}}") {
        fun createRoute(id: String, image: String) = "chat_with_ai/$id/$image"
    }

    data object ScreenInteractionRoutes:
        Routes("interaction/{$INTERACTION_PROMPT}/{$INTERACTION_IMAGE}/{$INTERACTION_MAKEUP_TYPE}") {
        fun createRoute(
            interactionPrompt: String,
            interactionImage: String,
            interactionMakeupType: String
        ) = "interaction/$interactionPrompt/$interactionImage/$interactionMakeupType"
    }

    data object ScreenMakeUpInstruction: Routes("makeup_instruction") {
        fun createRoute() = "makeup_instruction"
    }

    data object ScreenExpertsRecommended: Routes("experts_recommended/{${QUESTION_ID}}") {
        fun createRoute(id: String) = "experts_recommended/$id"
    }

    // ----------------------------------------------------------------------------

    /*
    -- Route /main/profile --
     */

    data object ScreenUserProfile : Routes("account") {
        fun createRoute() = "account"
    }

    data object ScreenBookingHistory : Routes("booking_history") {
        fun createRoute() = "booking_history"
    }

    // ----------------------------------------------------------------------------

    /*
    -- Route /booking_expert --
     */

    data object ScreenBookingExpert : Routes("booking_expert/{${EXPERT_ID}}") {
        fun createRoute(id: String) = "booking_expert/$id"
    }

    companion object {
        const val MAKE_UP_STYLIST_ID = "makeup_stylist_id"
        const val MAKE_UP_CATEGORY_ID = "makeup_category_id"
        const val MAKE_UP_TITLE_ID = "makeup_title_id"
        const val MAKE_UP_TEMPLATE_ID = "makeup_template_id"

        const val INTERACTION_PROMPT = "interaction_prompt"
        const val INTERACTION_IMAGE = "interaction_image"
        const val INTERACTION_MAKEUP_TYPE = "interaction_makeup_type"

        const val CONVERSATION_ID = "conversation_id"
        const val IMAGE_INIT_ID = "image_id"

        const val QUESTION_ID = "question_id"

        const val EXPERT_ID = "expert_id"


        const val ARG_INITIAL_PROMPT = "initialPrompt"
        const val ARG_INITIAL_LIST_PROMPT = "initialListPrompt"
    }
}