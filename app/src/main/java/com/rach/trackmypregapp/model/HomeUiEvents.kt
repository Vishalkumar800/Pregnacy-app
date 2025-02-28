package com.rach.trackmypregapp.model

sealed class HomeUiEvents {
    data class EnterSysBP(val value: String) : HomeUiEvents()
    data class EnterDiaBP(val value: String) : HomeUiEvents()
    data class EnterWeight(val value: String) : HomeUiEvents()
    data class EnterBabyKicks(val value: String) : HomeUiEvents()
    data class ShowDialog(val value: Boolean) : HomeUiEvents()
    object LoadAllData : HomeUiEvents()
    object UploadData : HomeUiEvents()
}