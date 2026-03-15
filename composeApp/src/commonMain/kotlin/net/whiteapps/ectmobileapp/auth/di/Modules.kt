package net.whiteapps.ectmobileapp.auth.di

import auth.domain.repository.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import net.whiteapps.ectmobileapp.auth.data.remote.AuthApi
import net.whiteapps.ectmobileapp.auth.data.repository.AuthRepositoryImpl
import net.whiteapps.ectmobileapp.auth.presentation.AuthViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


val AuthApiModule = module {
    single {AuthApi(get())}
}

val AuthRepositoryModule = module {
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}

val AuthViewModelModule = module {

    // New way using viewModelOf
    viewModelOf(::AuthViewModel)
}

val AuthModule = module {
    includes(AuthApiModule + AuthRepositoryModule + AuthViewModelModule)
}

