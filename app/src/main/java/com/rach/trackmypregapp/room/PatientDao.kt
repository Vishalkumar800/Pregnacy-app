package com.rach.trackmypregapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(patient: PatientEntity)

    @Query("SELECT * FROM patients ORDER By dateAndTime DESC")
    fun getAllData(): Flow<List<PatientEntity>>

}