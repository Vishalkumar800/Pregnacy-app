package com.rach.trackmypregapp.room.useCases

import com.rach.trackmypregapp.room.PatientDao
import com.rach.trackmypregapp.room.PatientEntity
import com.rach.trackmypregapp.room.repo.PatientRepository
import javax.inject.Inject

class InsertPatientUseCases @Inject constructor(private val patientRepository: PatientRepository) {

    suspend operator fun invoke(patientEntity: PatientEntity) {
        patientRepository.insertPatient(patient = patientEntity)
    }
}