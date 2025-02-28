package com.rach.trackmypregapp.appUi.viewmodel

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.util.trace
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.rach.trackmypregapp.model.HomeUiEvents
import com.rach.trackmypregapp.model.HomeUiState
import com.rach.trackmypregapp.room.PatientEntity
import com.rach.trackmypregapp.room.useCases.GetAllDataUsesCases
import com.rach.trackmypregapp.room.useCases.InsertPatientUseCases
import com.rach.trackmypregapp.room.workManager.NotificationWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val insertPatientUseCases: InsertPatientUseCases,
    private val getAllDataUsesCases: GetAllDataUsesCases,
    private val workManager: WorkManager,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    val snackBarHost = SnackbarHostState()

    fun onEvent(events: HomeUiEvents) {
        when (events) {
            is HomeUiEvents.EnterSysBP -> updateState { it.copy(sysBP = events.value) }
            is HomeUiEvents.EnterDiaBP -> updateState { it.copy(diaBp = events.value) }
            is HomeUiEvents.EnterWeight -> updateState { it.copy(weight = events.value) }
            is HomeUiEvents.EnterBabyKicks -> updateState { it.copy(kicks = events.value) }
            is HomeUiEvents.ShowDialog -> updateState { it.copy(showDialog = events.value) }

            HomeUiEvents.LoadAllData -> loadAllData()
            HomeUiEvents.UploadData -> uploadData()
        }


    }

    private fun updateState(update: (HomeUiState) -> HomeUiState) {
        viewModelScope.launch {
            _state.value = update(_state.value)
        }
    }

    private fun uploadData() {

        viewModelScope.launch {

            try {
                if (validateFields()) {
                    updateState { it.copy(isLoading = true) }

                    delay(600)
                    val currentTime =
                        SimpleDateFormat("EEE, dd MMM yyyy hh:mm a", Locale.getDefault())
                            .format(Date())

                    val patient = PatientEntity(
                        sysBP = state.value.sysBP,
                        diaBP = state.value.diaBp,
                        weight = state.value.weight,
                        babyKicks = state.value.kicks,
                        dateAndTime = currentTime
                    )

                    insertPatientUseCases(patientEntity = patient)
                    scheduleNotification(
                        title = "Time to log your vitals!",
                        message = "Stay on top of your health. Please update your vitals now!"
                    )

                    //Reset Fields
                    updateState { it.copy(isLoading = false , showDialog = false) }
                    resetField()

                } else {
                    updateState { it.copy(isLoading = false) }
                    snackBarHost.showSnackbar(message = "All fields are required")

                }
            } catch (e: Exception) {
                updateState { it.copy(isLoading = false) }
                snackBarHost.showSnackbar(message = "${e.message}")
            }
        }

    }

    private fun scheduleNotification(
        title: String,
        message: String
    ) {
        val inputData = Data.Builder()
            .putString("title", title)
            .putString("message", message)
            .build()

        val notificationWork = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInputData(inputData)
            .setInitialDelay(5, TimeUnit.HOURS)
            .build()

        workManager.enqueue(notificationWork)

    }

    private fun loadAllData() {
        viewModelScope.launch {
            try {
                getAllDataUsesCases().collect { data ->
                    updateState { it.copy(allData = data) }
                }
            } catch (e: Exception) {
                snackBarHost.showSnackbar(message = "${e.message}")
            }
        }
    }

    private suspend fun validateFields(): Boolean {
        return if (
            state.value.sysBP.isEmpty() || state.value.diaBp.isEmpty() || state.value.weight.isEmpty() || state.value.kicks.isEmpty()
        ) {
            snackBarHost.showSnackbar(message = "All fields are required")
            false
        } else {
            true
        }
    }

    private fun resetField() {
        updateState {
            it.copy(
                sysBP = "",
                diaBp = "",
                weight = "",
                kicks = ""
            )
        }
    }
}