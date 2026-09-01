package com.thenoman.app.features.permissions.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.thenoman.app.features.permissions.data.PermissionItem
import com.thenoman.app.features.permissions.data.listOfRequiredPermissions
import com.thenoman.app.features.permissions.domain.PermissionChecker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PermissionItemState (
    val item: PermissionItem,
    val isGranted: Boolean = false
)

data class RequestPermissionsUiState(
    val requiredPermissions: List<PermissionItemState> = emptyList(),
    val areAllPermissionsGranted: Boolean = false,
)

class RequestPermissionsViewModel(
    application: Application
): AndroidViewModel(application) {
    private val permissionChecker = PermissionChecker(application)

    private val _uiState = MutableStateFlow(RequestPermissionsUiState())
    val uiState: StateFlow<RequestPermissionsUiState> = _uiState.asStateFlow()

    init {
        reloadPermissions()
    }

    fun reloadPermissions() {
        val newItemStates: List<PermissionItemState> = listOfRequiredPermissions.map {
            PermissionItemState(
                item = it,
                isGranted = permissionChecker.checkPermissionForIntent(it.permissionIntent)
            )
        }

        _uiState.value = RequestPermissionsUiState(
            requiredPermissions = newItemStates,
            areAllPermissionsGranted = newItemStates.all { it.isGranted }
        )
    }

    fun verifyPermissions(): Boolean {
        reloadPermissions()

        return uiState.value.areAllPermissionsGranted
    }
}