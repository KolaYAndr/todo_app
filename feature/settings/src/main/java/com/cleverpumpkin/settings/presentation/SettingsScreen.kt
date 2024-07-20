package com.cleverpumpkin.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cleverpumpkin.cor.presentation.theme.TodoAppTheme
import com.cleverpumpkin.settings.R

@Composable
fun SettingsScreen(modifier: Modifier = Modifier, onNavigation: () -> Unit) {
    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .clip(RoundedCornerShape(8.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.settings),
                    tint = TodoAppTheme.colorScheme.blue
                )
                Text(
                    text = stringResource(R.string.settings),
                    color = TodoAppTheme.colorScheme.labelPrimary,
                    style = TodoAppTheme.typography.body
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigation() }
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = stringResource(R.string.about_app),
                    tint = TodoAppTheme.colorScheme.blue
                )
                Text(
                    text = stringResource(R.string.about_app),
                    color = TodoAppTheme.colorScheme.labelPrimary,
                    style = TodoAppTheme.typography.body
                )
            }
        }
    }
}