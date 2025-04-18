package com.iec.makeup.ui.features.authentication.third_party_auth

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


const val WEB_CLIENT_ID = "148489318698-o3r7t3miqgp6u3pq2cog1a5l1a4fgi6t.apps.googleusercontent.com"

// GOOGLE SDK

//class GoogleAuthHelper @Inject constructor(
//    @ApplicationContext context: ApplicationContext
//) {
//    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
//        .setFilterByAuthorizedAccounts(true)
//        .setServerClientId(WEB_CLIENT_ID)
//        .setAutoSelectEnabled(true)
//        .setNonce("GlamAura")
//        .build()
//}