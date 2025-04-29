package com.iec.makeup.di

import com.iec.makeup.core.DataStoreInterface
import com.iec.makeup.core.PersistentState
import com.iec.makeup.core.network.AuthInterceptorWithToken
import com.iec.makeup.core.network.TokenManager
import com.iec.makeup.core.utils.Constants.BASE_URL
import com.iec.makeup.core.utils.Constants.TIME_OUT
import com.iec.makeup.data.remote.api.AuthEndpoint
import com.iec.makeup.data.remote.api.ChatbotEndpoint
import com.iec.makeup.data.remote.api.ExpertEndpoint
import com.iec.makeup.data.remote.api.MakeUpTempCategoryEndpoint
import com.iec.makeup.data.remote.api.MakeUpTemplateEndpoint
import com.iec.makeup.data.remote.api.PromptEndpoint
import com.iec.makeup.data.remote.api.UserEndpoint
import com.iec.makeup.data.repository_implement.ExpertRepositoryImpl
import com.iec.makeup.data.repository_implement.MakeUpTemplateCategoryImpl
import com.iec.makeup.data.repository_implement.MakeUpTemplateRepositoryImpl
import com.iec.makeup.data.repository_implement.MessageChatRemoteImpl
import com.iec.makeup.data.repository.AuthRepository
import com.iec.makeup.data.repository.AuthRepositoryImpl
import com.iec.makeup.data.repository.ExpertRepository
import com.iec.makeup.data.repository.MakeUpTemplateCategoryRepository
import com.iec.makeup.data.repository.MakeUpTemplateRepository
import com.iec.makeup.data.repository.MessageChatRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    // Network Module


    @Provides
    fun provideAuthInterceptor(
        tokenManager: TokenManager
    ): AuthInterceptorWithToken = AuthInterceptorWithToken(tokenManager)


    @Provides
    @Singleton
    @Named("NoAuth")
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val authInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .header("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .build()


        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Named("Auth")
    fun provideAuthRetrofitWithToken(
        authInterceptor: AuthInterceptorWithToken
    ): Retrofit {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    /*
     - Retrofit Module Injection
     */
    @Provides
    @Singleton
    fun provideAuthEndpoint(@Named("NoAuth") retrofit: Retrofit): AuthEndpoint =
        retrofit.create(AuthEndpoint::class.java)

    @Provides
    @Singleton
    fun provideUserEndpoint(@Named("Auth") retrofit: Retrofit): UserEndpoint =
        retrofit.create(UserEndpoint::class.java)

    @Provides
    @Singleton
    fun provideMakeUpTempCategoryEndpoint(@Named("Auth") retrofit: Retrofit): MakeUpTempCategoryEndpoint =
        retrofit.create(MakeUpTempCategoryEndpoint::class.java)

    @Provides
    @Singleton
    fun provideMakeUpTemplateEndpoint(@Named("Auth") retrofit: Retrofit): MakeUpTemplateEndpoint =
        retrofit.create(MakeUpTemplateEndpoint::class.java)

    @Provides
    @Singleton
    fun providePromptEndpoint(@Named("Auth") retrofit: Retrofit): PromptEndpoint =
        retrofit.create(PromptEndpoint::class.java)


    @Provides
    @Singleton
    fun provideExpertEndpoint(@Named("Auth") retrofit: Retrofit): ExpertEndpoint =
        retrofit.create(ExpertEndpoint::class.java)

    @Provides
    @Singleton
    fun provideAIEndpoint(@Named("Auth") retrofit: Retrofit): ChatbotEndpoint =
        retrofit.create(ChatbotEndpoint::class.java)
}

@InstallIn(SingletonComponent::class)
@Module
abstract class ImplementationsModule {

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindDataStore(dataStore: PersistentState): DataStoreInterface

    @Binds
    abstract fun bindMessageChatRepository(messageChatRepositoryImpl: MessageChatRemoteImpl): MessageChatRepository

    @Binds
    abstract fun bindMakeUpTemplateCategory(makeUpTemplateCategoryImpl: MakeUpTemplateCategoryImpl): MakeUpTemplateCategoryRepository

    @Binds
    abstract fun bindMakeUpTemplate(makeUpTemplateImpl: MakeUpTemplateRepositoryImpl): MakeUpTemplateRepository

    @Binds
    abstract fun bindExpertRepo(expertRepositoryImpl: ExpertRepositoryImpl): ExpertRepository
}