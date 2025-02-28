package com.rach.trackmypregapp.appUi

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rach.trackmypregapp.AppPreview
import com.rach.trackmypregapp.R
import com.rach.trackmypregapp.appUi.components.CustomFloatingActionButton
import com.rach.trackmypregapp.appUi.components.CustomTopAppBar
import com.rach.trackmypregapp.appUi.viewmodel.MainViewModel
import com.rach.trackmypregapp.model.HomeUiEvents
import com.rach.trackmypregapp.room.PatientEntity
import com.rach.trackmypregapp.ui.theme.InterfontFamily
import com.rach.trackmypregapp.ui.theme.TrackMyPregAppTheme
import com.rach.trackmypregapp.ui.theme.floatColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun HomeBody(
    viewModel: MainViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {

        viewModel.onEvent(events = HomeUiEvents.LoadAllData)

    }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            CustomFloatingActionButton(onClick = { viewModel.onEvent(HomeUiEvents.ShowDialog(true)) })
        },
        topBar = {
            CustomTopAppBar()
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                if (state.allData.isNotEmpty()) {
                    items(state.allData) { data ->
                        HomeCard(
                            patientEntity = data,
                            modifier = Modifier.padding(
                                dimensionResource(R.dimen.normal_padding)
                            )
                        )
                    }
                }
            }

            // No found Data TEXT
            if (state.allData.isEmpty()) {
                Text(
                    text = "No Data Found",
                    modifier = Modifier
                        .align(Alignment.Center) // Center the text
                )
            }
        }
    }

    //Alert Box Ji
    if (state.showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(HomeUiEvents.ShowDialog(false)) },
            confirmButton = {},
            text = {
                DialogSection(
                    modifier = Modifier
                        .fillMaxWidth(),
                    viewModel = viewModel,
                    submit = {
                        scope.launch {
                            viewModel.onEvent(HomeUiEvents.UploadData)
                        }
                    }
                )
            }
        )
    }

}
@Composable
private fun HomeCard(patientEntity: PatientEntity, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(Color(0xFFEBB9FE))
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.normal_padding))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    CardSingleItem(icon = R.drawable.heart_rate, title = "${patientEntity.sysBP} bpm")
                    Spacer(modifier = Modifier.height(10.dp))
                    CardSingleItem(icon = R.drawable.scale, title = "${patientEntity.weight} kg")
                }
                Column(modifier = Modifier.weight(1f)) {
                    CardSingleItem(icon = R.drawable.bloodpressure, title = "${patientEntity.diaBP}/80 mmHg")
                    Spacer(modifier = Modifier.height(10.dp))
                    CardSingleItem(icon = R.drawable.baby, title = "${patientEntity.babyKicks} Kicks")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            DateAndTimeSection(date = patientEntity.dateAndTime)
        }
    }
}


@Composable
private fun DateAndTimeSection(
    date: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(floatColor)
    ) {
        Text(
            text = date,
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            textAlign = TextAlign.End,
            color = Color.White
        )
    }
}

@Composable
private fun CardSingleItem(
    @DrawableRes icon: Int,
    title: String,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "icon ",
            modifier = Modifier.size(30.dp),
            tint =  Color.Black
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            fontFamily = InterfontFamily,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
    }

}

@AppPreview
@Composable
private fun Preview() {
    TrackMyPregAppTheme {
        HomeBody()
    }
}