package com.rach.trackmypregapp.model

import com.rach.trackmypregapp.room.PatientEntity

data class HomeUiState(
    val isLoading: Boolean = false,
    val showDialog: Boolean = false,
    val sysBP: String = "",
    val diaBp: String = "",
    val weight: String = "",
    val kicks: String = "",
    val allData:List<PatientEntity> = emptyList()
)