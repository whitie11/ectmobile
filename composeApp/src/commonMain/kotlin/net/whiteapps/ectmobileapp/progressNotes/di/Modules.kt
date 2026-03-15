package net.whiteapps.ectmobileapp.progressNotes.di

import net.whiteapps.ectmobileapp.progressNotes.data.remote.ProgressNotesAPI
import net.whiteapps.ectmobileapp.progressNotes.data.repository.ProgressNotesRepositoryImpl
import net.whiteapps.ectmobileapp.progressNotes.domain.repository.ProgressNotesRepository
import net.whiteapps.ectmobileapp.progressNotes.presentation.ProgressNotesViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val ProgressNotesViewModelModule = module {
    viewModelOf(::ProgressNotesViewModel)
    }

val ProgressNotesAPIModule = module {
    single { ProgressNotesAPI(get()) }
    }

val ProgressNotesRepositoryModule = module {
    singleOf(::ProgressNotesRepositoryImpl).bind<ProgressNotesRepository>()
}

val ProgressNotesModule = module {
    includes(ProgressNotesViewModelModule + ProgressNotesAPIModule + ProgressNotesRepositoryModule)
}




