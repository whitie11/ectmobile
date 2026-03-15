package net.whiteapps.ectmobileapp.referral.di

import net.whiteapps.ectmobileapp.referral.presentation.viewModels.ReferralViewModel
import net.whiteapps.ectmobileapp.referral.data.remote.ReferralAPI
import net.whiteapps.ectmobileapp.referral.data.repository.ReferralRepositoryImpl
import net.whiteapps.ectmobileapp.referral.domain.repository.ReferralRepository
import net.whiteapps.ectmobileapp.referral.presentation.viewModels.NewReferralViewModel
import net.whiteapps.ectmobileapp.referral.presentation.viewModels.ReferralDetailsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


val ReferralViewModelModule = module {
    viewModelOf(::ReferralViewModel)
}

val ReferralDetailsViewModelModule = module {
    viewModelOf(::ReferralDetailsViewModel)
}

val NewReferralViewModelModule = module {
    viewModelOf(::NewReferralViewModel)
}

val RefAPIModule = module {
    single { ReferralAPI(get(), get()) }
}

val RefRepositoryModule = module {
    singleOf(::ReferralRepositoryImpl).bind<ReferralRepository>()
}

val ReferralModule = module {
    includes(
        RefAPIModule
                + RefRepositoryModule
                + ReferralViewModelModule
                + ReferralDetailsViewModelModule
                + NewReferralViewModelModule
    )
}