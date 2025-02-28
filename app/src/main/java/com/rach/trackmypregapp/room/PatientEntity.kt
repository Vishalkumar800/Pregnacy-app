package com.rach.trackmypregapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "patients")
data class PatientEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sysBP: String,
    val diaBP: String,
    val weight: String,
    val babyKicks: String,
    val dateAndTime: String
)
