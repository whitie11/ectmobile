package net.whiteapps.ectmobileapp.progressNotes.domain.repository

import net.whiteapps.ectmobileapp.progressNotes.data.dto.ProgressNotesResponse
import net.whiteapps.ectmobileapp.progressNotes.domain.models.ProgressNote
import net.whiteapps.ectmobileapp.util.AppResult

interface ProgressNotesRepository {
    suspend fun getRProgressNotesReferral(refId: Int): AppResult<List<ProgressNotesResponse>>
}