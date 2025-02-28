package com.rach.trackmypregapp.appUi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rach.trackmypregapp.AppPreview
import com.rach.trackmypregapp.R
import com.rach.trackmypregapp.appUi.components.CustomOutlinedField
import com.rach.trackmypregapp.appUi.viewmodel.MainViewModel
import com.rach.trackmypregapp.model.HomeUiEvents
import com.rach.trackmypregapp.ui.theme.InterfontFamily
import com.rach.trackmypregapp.ui.theme.TrackMyPregAppTheme

@Composable
fun DialogSection(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    submit: () -> Unit
) {

    val state by viewModel.state.collectAsState()

    Column {

        Text(
            text = stringResource(R.string.ad_vitals),
            modifier = Modifier.padding(bottom = 10.dp),
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = InterfontFamily,
            fontWeight = FontWeight.SemiBold,
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CustomOutlinedField(
                value = state.sysBP,
                onValueChange = {
                    viewModel.onEvent(HomeUiEvents.EnterSysBP(it))
                },
                label = R.string.sys_bp,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.weight(1f)
            )

            CustomOutlinedField(
                value = state.diaBp,
                onValueChange = {
                    viewModel.onEvent(HomeUiEvents.EnterDiaBP(it))
                },
                label = R.string.dia_bp,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.normal_spacing)))
        CustomOutlinedField(
            modifier = Modifier.fillMaxWidth(),
            value = state.weight,
            onValueChange = {
                viewModel.onEvent(HomeUiEvents.EnterWeight(it))
            },
            label = R.string.weight_in_kg,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.normal_spacing)))
        CustomOutlinedField(
            modifier = Modifier.fillMaxWidth(),
            value = state.kicks,
            onValueChange = {
                viewModel.onEvent(HomeUiEvents.EnterBabyKicks(it))
            },
            label = R.string.baby_kicks,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )
        Spacer(modifier = Modifier.height(60.dp))

        SnackbarHost(
            hostState = viewModel.snackBarHost,
            modifier = modifier
        )

        Button(
            onClick = {
                submit()
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)

        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = Color.White
                )
            } else {
                Text(
                    "Submit",
                    fontWeight = FontWeight.Medium,
                    fontFamily = InterfontFamily
                )
            }
        }

    }


}

