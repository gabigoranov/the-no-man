package com.thenoman.app.features.permissions.presentation

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thenoman.app.features.permissions.data.requiredPermissions
import com.thenoman.app.ui.theme.dimens
import com.thenoman.app.ui.theme.spacing

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RequestPermissionsScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            Button(
                content = {
                    Text("Next")
                },
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium)
                    .height(ButtonDefaults.MediumContainerHeight)
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(MaterialTheme.spacing.medium),

            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            Text(
                text = "Welcome",
                style = MaterialTheme.typography.headlineLarge,
            )

            Text(
                text = "The No Man needs a few permissions to work properly",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )

            // List of required permissions
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                requiredPermissions.mapIndexed { idx, it ->
                    SegmentedListItem(
                        content = { Text(it.title) },
                        colors = ListItemDefaults.segmentedColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                        ),
                        supportingContent = {
                            Text(
                                it.description,
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        leadingContent = {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = it.iconDescription,
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        shape = CircleShape
                                    )
                                    .padding(MaterialTheme.spacing.small)
                                    .size(MaterialTheme.dimens.iconMedium)
                            )
                        },
                        trailingContent = {
                            Button(
                                content = { Text("Grant") },
                                onClick = {}
                            )
                        },
                        shapes = ListItemDefaults.segmentedShapes(
                            index = idx,
                            count = requiredPermissions.count()
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    )
                }
            }

            Text(
                text = "Without the required permissions, some or all features may not work correctly. Any and all data read remains on-device without being collected. Grant all permissions to proceed.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.weight(1f))

        }
    }
}