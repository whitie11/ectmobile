package net.whiteapps.ectmobileapp.serviceUser.di

import net.whiteapps.ectmobileapp.referral.data.remote.ReferralAPI
import net.whiteapps.ectmobileapp.referral.data.repository.ReferralRepositoryImpl
import net.whiteapps.ectmobileapp.referral.domain.repository.ReferralRepository
import net.whiteapps.ectmobileapp.serviceUser.data.remote.ServiceUserAPI
import net.whiteapps.ectmobileapp.serviceUser.data.repository.ServiceUserRepositoryImpl
import net.whiteapps.ectmobileapp.serviceUser.domain.repository.ServiceUserRepository
import net.whiteapps.ectmobileapp.serviceUser.presentation.viewModels.NewSUViewModel
import net.whiteapps.ectmobileapp.serviceUser.presentation.viewModels.SelectServiceUserViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val NewSUViewModelModule = module {
    viewModelOf(::NewSUViewModel)
}

val SelectSUViewModelModule = module {
    viewModelOf(::SelectServiceUserViewModel)
}
val SUAPIModule = module {
    single { ServiceUserAPI(get()) }
}

val SURepositoryModule = module {
    singleOf(::ServiceUserRepositoryImpl).bind<ServiceUserRepository>()
}
val NewSUModule = module {
    includes(
        SUAPIModule
                + SURepositoryModule
                + NewSUViewModelModule
                + SelectSUViewModelModule
    )
}