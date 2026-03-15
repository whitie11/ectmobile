package net.whiteapps.ectmobileapp.progressNotes.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.io.IOException
import net.whiteapps.ectmobileapp.progressNotes.data.dto.ProgressNotesResponse
import net.whiteapps.ectmobileapp.progressNotes.domain.models.ProgressNote
import net.whiteapps.ectmobileapp.progressNotes.domain.repository.ProgressNotesRepository
import net.whiteapps.ectmobileapp.util.AppResult
import net.whiteapps.ectmobileapp.util.Resource

class GetProgressNotesReferralUseCase (
    private val refId: Int,
    private val progressNotesRepository: ProgressNotesRepository,
    ) {
        operator fun invoke(refId: Int): Flow<Resource<List<ProgressNotesResponse>>> = flow {
            try {
                emit(Resource.Loading())
                when(val res = progressNotesRepository.getRProgressNotesReferral(refId)) {
                    is AppResult.Error ->   {
                        emit(Resource.Error(res.msg))
                    }
                    is AppResult.Success<List<ProgressNotesResponse>> -> {
                        val it = res.data
                        emit(Resource.Success(it))
                    }
                }
            } catch(e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            } catch(e: Throwable ) {
                emit(Resource.Error(e.message?: "Something went badly wrong!!"))
            }

        }

}