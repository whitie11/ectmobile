package net.whiteapps.ectmobileapp.common.di

import homeFeature.di.HomeViewModelModule
import net.whiteapps.ectmobileapp.auth.di.AuthModule
import net.whiteapps.ectmobileapp.progressNotes.di.ProgressNotesModule
import net.whiteapps.ectmobileapp.referral.di.ReferralModule
import net.whiteapps.ectmobileapp.serviceUser.di.NewSUModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            AuthModule,
            provideLocalSettingsRepository,
            networkModule,
            HomeViewModelModule,
            ReferralModule,
            ProgressNotesModule,
            NewSUModule
        )

    }
}