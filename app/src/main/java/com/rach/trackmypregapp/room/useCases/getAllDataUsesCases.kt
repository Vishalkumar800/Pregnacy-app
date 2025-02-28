package com.rach.trackmypregapp.room.useCases

import com.rach.trackmypregapp.room.PatientEntity
import com.rach.trackmypregapp.room.repo.PatientRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllDataUsesCases @Inject constructor(
    private val repository: PatientRepository
) {

   operator fun invoke():Flow<List<PatientEntity>>{
       return repository.allPatients
   }

}