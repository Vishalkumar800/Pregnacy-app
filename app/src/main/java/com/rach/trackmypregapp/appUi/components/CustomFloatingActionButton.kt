package com.rach.trackmypregapp.appUi.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rach.trackmypregapp.R
import com.rach.trackmypregapp.ui.theme.floatColor

@Composable
fun CustomFloatingActionButton(onClick: () -> Unit, modifier: Modifier = Modifier) {

    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(30.dp),
        containerColor = floatColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.floatingButton),
            tint = androidx.compose.ui.graphics.Color.White
        )
    }

}