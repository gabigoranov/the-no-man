package com.thenoman.app.features.permissions.presentation

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thenoman.app.features.permissions.data.listOfRequiredPermissions
import com.thenoman.app.ui.theme.dimens
import com.thenoman.app.ui.theme.spacing
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thenoman.app.features.permissions.viewmodel.RequestPermissionsViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RequestPermissionsScreen(
    modifier: Modifier = Modifier,
    viewModel: RequestPermissionsViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    //TODO: Fix bug where you need to enter and exit twice
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.reloadPermissions()
    }

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
                uiState.value.requiredPermissions.mapIndexed { idx, it ->
                    SegmentedListItem(
                        content = { Text(it.item.title) },
                        colors = ListItemDefaults.segmentedColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        supportingContent = {
                            Text(
                                it.item.description,
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        leadingContent = {
                            Icon(
                                imageVector = it.item.icon,
                                contentDescription = it.item.iconDescription,
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
                            if (it.isGranted) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "Permission is granted",
                                    modifier = modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.primaryContainer,
                                            shape = CircleShape
                                        )
                                        .padding(MaterialTheme.spacing.extraSmall)
                                )
                            } else {
                                Button(
                                    content = { Text("Grant") },
                                    onClick = {
                                        val intent = Intent(it.item.permissionIntent).apply {
                                            data = "package:${context.packageName}".toUri()
                                        }

                                        context.startActivity(intent)
                                    }
                                )
                            }
                        },
                        shapes = ListItemDefaults.segmentedShapes(
                            index = idx,
                            count = listOfRequiredPermissions.count()
                        ),
                        onClick = {
                            val intent = Intent(it.item.permissionIntent).apply {
                                data = "package:${context.packageName}".toUri()
                            }

                            context.startActivity(intent)
                        }
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