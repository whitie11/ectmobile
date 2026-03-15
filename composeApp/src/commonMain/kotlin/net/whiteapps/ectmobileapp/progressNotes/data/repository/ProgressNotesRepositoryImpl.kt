package net.whiteapps.ectmobileapp.progressNotes.data.repository

import net.whiteapps.ectmobileapp.progressNotes.data.dto.ProgressNotesResponse
import net.whiteapps.ectmobileapp.progressNotes.data.remote.ProgressNotesAPI
import net.whiteapps.ectmobileapp.progressNotes.domain.models.ProgressNote
import net.whiteapps.ectmobileapp.progressNotes.domain.repository.ProgressNotesRepository
import net.whiteapps.ectmobileapp.util.AppResult

class ProgressNotesRepositoryImpl(
    private val api: ProgressNotesAPI
): ProgressNotesRepository {
    override suspend fun getRProgressNotesReferral(refId: Int): AppResult<List<ProgressNotesResponse>> {
        return  api.getProgressNotesReferral(refId)
    }
}