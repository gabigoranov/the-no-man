package com.thenoman.app.features.permissions.presentation

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thenoman.app.features.permissions.data.listOfRequiredPermissions
import com.thenoman.app.features.permissions.viewmodel.PermissionItemState
import com.thenoman.app.features.permissions.viewmodel.RequestPermissionsViewModel
import com.thenoman.app.ui.theme.dimens
import com.thenoman.app.ui.theme.spacing

@Composable
fun RequestPermissionsScreen(
    modifier: Modifier = Modifier,
    viewModel: RequestPermissionsViewModel = viewModel(),
    onNavigateToHome: () -> Unit,
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    //TODO: Fix bug where you need to enter and exit twice
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.reloadPermissions()
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NextButton(
                isEnabled = uiState.areAllPermissionsGranted,
                verifyPermissions = viewModel::verifyPermissions,
                onClicked = onNavigateToHome
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
            HeaderWelcomeMessage()

            // List of required permissions
            SegmentedListOfPermissions(uiState.requiredPermissions) { permItem -> launchIntent(permItem, context) }

            InfoWarningMessage()
        }
    }
}

@Composable
private fun NextButton(
    isEnabled: Boolean,
    verifyPermissions: () -> Boolean,
    onClicked: () -> Unit
) {
    Button(
        content = { Text("Next") },
        enabled = isEnabled,
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium)
            .height(ButtonDefaults.MediumContainerHeight),
        onClick = {
            // Verify all permissions are granted in case the user tries to trick the app
            if(!verifyPermissions()){
                return@Button
            }

            onClicked()
        },
    )
}

@Composable
private fun SegmentedListOfPermissions(
    requiredPermissions: List<PermissionItemState>,
    onPermissionClicked: (PermissionItemState) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
    ) {
        requiredPermissions.mapIndexed { idx, it ->
            SegmentedListItem(
                content = { Text(it.item.title) },
                colors = ListItemDefaults.segmentedColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                ),
                verticalAlignment = Alignment.CenterVertically,
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
                supportingContent = {
                    Text(
                        it.item.description,
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                trailingContent = {
                    when (it.isGranted) {
                        true -> PermissionGrantedTick()
                        false -> GrantPermissionButton { onPermissionClicked(it) }
                    }
                },
                shapes = ListItemDefaults.segmentedShapes(
                    index = idx,
                    count = listOfRequiredPermissions.count()
                ),
                onClick = {
                    onPermissionClicked(it)
                }
            )
        }
    }
}

@Composable
private fun GrantPermissionButton(
    onClicked: () -> Unit
) {
    Button(
        content = { Text("Grant") },
        onClick = onClicked
    )
}

private fun launchIntent(
    it: PermissionItemState,
    context: Context
) {
    val intent = when (it.item.intentRequiresPackageExtension) {
        // Certain intents leading to settings screens require a specified package
        true -> Intent(it.item.permissionIntent).apply {
            data = "package:${context.packageName}".toUri()
        }

        false -> Intent(it.item.permissionIntent)
    }

    context.startActivity(intent)
}

@Composable
private fun PermissionGrantedTick() {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Done,
            contentDescription = "Permission is granted",
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                )
                .padding(MaterialTheme.spacing.extraSmall)
        )
    }
}

@Composable
private fun HeaderWelcomeMessage() {
    Text(
        text = "Welcome",
        style = MaterialTheme.typography.headlineLarge,
    )

    Text(
        text = "The No Man needs a few permissions to work properly",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.secondary
    )
}

@Composable
private fun InfoWarningMessage() {
    Text(
        text = "Without the required permissions, some or all features may not work correctly. Any and all data read remains on-device without being collected. Grant all permissions to proceed.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.secondary
    )
}