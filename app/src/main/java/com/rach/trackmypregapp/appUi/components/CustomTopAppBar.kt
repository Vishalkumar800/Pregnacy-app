package com.rach.trackmypregapp.appUi.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rach.trackmypregapp.R
import com.rach.trackmypregapp.ui.theme.InterfontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                stringResource(R.string.top_app_title),
                modifier = Modifier.padding(start = 50.dp),
                fontFamily = InterfontFamily,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Home icon",
                modifier = Modifier.padding(start = 5.dp)
            )

        },
        colors = TopAppBarDefaults.topAppBarColors(
            Color(0xFFC8ADFC)
        )

    )
}