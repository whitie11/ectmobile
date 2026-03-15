package net.whiteapps.ectmobileapp.common.di


import net.whiteapps.ectmobileapp.common.data.LocalSettingsRepositoryImpl
import net.whiteapps.ectmobileapp.common.domain.repository.LocalSettingsRepository
//import net.whiteapps.ectmobileapp.common.network.AuthInterceptor
import net.whiteapps.ectmobileapp.common.network.provideHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val provideLocalSettingsRepository = module {
    singleOf(::LocalSettingsRepositoryImpl).bind<LocalSettingsRepository>()
}

//val provideAuthInterceptor = module {
//    single { AuthInterceptor(get()) }
//}
//
//val provideAuthAuthenticator = module {
//    single { AuthInterceptor(get()) }
//}

val networkModule = module { single {provideHttpClient(get()) }}



