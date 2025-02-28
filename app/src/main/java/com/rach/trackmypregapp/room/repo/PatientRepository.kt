package com.rach.trackmypregapp.room.repo

import com.rach.trackmypregapp.room.PatientDao
import com.rach.trackmypregapp.room.PatientEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PatientRepository @Inject constructor(
    private val patientDao: PatientDao
) {

    val allPatients :Flow<List<PatientEntity>> = patientDao.getAllData()

    suspend fun insertPatient(patient:PatientEntity){
        patientDao.insert(patient =patient)
    }

}